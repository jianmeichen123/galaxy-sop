package com.galaxyinternet.common.interceptor;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galaxyinternet.common.utils.StrUtils;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class StatementInterceptor implements Interceptor {
	private static final Logger logger = LoggerFactory.getLogger(StatementInterceptor.class);

	private Map<String,String> tableMap = new HashMap<>();
	@Override
	public Object intercept(Invocation inv) throws Throwable {
		List<String> talbes = AuthContext.get().getTables();
		List<Long> userIds = AuthContext.get().getUserIds();
		if(talbes == null || talbes.size() == 0 || userIds == null || userIds.size() == 0)
		{
			return inv.proceed();
		}
		
		StatementHandler statementHandler = (StatementHandler) inv.getTarget();
		BoundSql boundSql = statementHandler.getBoundSql();
		if (boundSql == null || boundSql.getSql() == null || "".equals(boundSql.getSql()))
		{
			return null;
		}
		String sql = boundSql.getSql();
		if (!(sql.toUpperCase().trim().startsWith("SELECT")))
		{
			return inv.proceed();
		}
		
		MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, new DefaultObjectFactory(), new DefaultObjectWrapperFactory(), new DefaultReflectorFactory());
		sql = handleSQL(sql,userIds);
		metaStatementHandler.setValue("delegate.boundSql.sql", sql);
		return inv.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties arg0) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * SQL添加限制条件
	 * @param sql
	 * @param userIds
	 * @return
	 */
	private String handleSQL(String sql, List<Long> userIds)
	{
		try {
			Select select = (Select) CCJSqlParserUtil.parse(sql);
			if(PlainSelect.class.isInstance(select.getSelectBody()))
			{
				PlainSelect plainSelect = (PlainSelect)select.getSelectBody();
				FromItem from = plainSelect.getFromItem();
				List<Join> joins = plainSelect.getJoins();
				List<Table> tables = new ArrayList<>();
				tables.add((Table)from);
				
				if(joins != null && joins.size()>0)
				{
					for(Join join : joins)
					{
						tables.add((Table)join.getRightItem());
					}
				}
				List<String> tableRestriction = AuthContext.get().getTables();
				for(Table table : tables)
				{
					String tableName = table.getFullyQualifiedName();
					String alias = table.getAlias() != null ? table.getAlias().getName() : table.getFullyQualifiedName();
					if(tableMap.containsKey(tableName) && tableRestriction.contains(tableName))
					{
						Expression where = plainSelect.getWhere();
						String condition = StrUtils.join(",", userIds.toArray());
						String cond = "";
						if(where == null)
						{
							cond = " "+alias+"."+tableMap.get(tableName)+" in ("+condition+") ";
						}
						else
						{
							cond += where.toString()+" and "+alias+"."+tableMap.get(tableName)+" in ("+condition+") ";
						}
						Expression exp = CCJSqlParserUtil.parseCondExpression(cond);
						plainSelect.setWhere(exp);
					}
				}
				return plainSelect.toString();
			}
		} catch (JSQLParserException e) 
		{
			logger.error("构建sql错误 "+sql,e);
		}
		
		return sql;
	}
	
	public Map<String, String> getTableMap() {
		return tableMap;
	}

	public void setTableMap(Map<String, String> tableMap) {
		this.tableMap = tableMap;
	}
	
	
}
