package com.galaxyinternet.common.interceptor;

import java.sql.Connection;
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
import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.deparser.ExpressionDeParser;
import net.sf.jsqlparser.util.deparser.SelectDeParser;
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class StatementInterceptor implements Interceptor {
	private static final Logger logger = LoggerFactory.getLogger(StatementInterceptor.class);

	private Map<String,String> tableMap = new HashMap<>();
	@Override
	public Object intercept(Invocation inv) throws Throwable {
		String pageId = AuthContext.get().getPageId();
		List<Integer> userIds = AuthContext.get().getUserIds();
		if(pageId == null || pageId.equals("index") || userIds == null || userIds.size() == 0)
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
	private String handleSQL(String sql, List<Integer> userIds)
	{
		StringBuilder buffer = new StringBuilder();
		try {
			Select select = (Select) CCJSqlParserUtil.parse(sql);
			ExpressionDeParser expressionDeParser = new ExpressionDeParser();
			SelectDeParser deparser = new GalaxySQLParser(expressionDeParser, buffer, userIds);
			expressionDeParser.setSelectVisitor(deparser);
			expressionDeParser.setBuffer(buffer);
			select.getSelectBody().accept(deparser);
		} catch (JSQLParserException e) {
			logger.error("构建sql错误 "+buffer,e);
			buffer = new StringBuilder(sql);
		}
		
		return buffer.toString();
	}
	
	class GalaxySQLParser extends SelectDeParser
	{
		private String condition;
		public GalaxySQLParser(ExpressionVisitor expressionVisitor, StringBuilder buffer, List<Integer> userIds) {
	        super(expressionVisitor, buffer);
	        this.condition = StrUtils.join(",", userIds.toArray());
	    }
		@Override
		public void visit(Table table) {
			String tableName = table.getFullyQualifiedName();
			String alias = table.getAlias() != null ? table.getAlias().getName() : table.getFullyQualifiedName();
			Map<String,String> tableMap = StatementInterceptor.this.getTableMap();
			if(tableMap.containsKey(tableName))
			{
				getBuffer().append(" (select * from "+tableName+" where "+tableMap.get(tableName)+" in ("+condition+") ) "+alias);
			}
			else
			{
				super.visit(table);
			}
		}
	}

	public Map<String, String> getTableMap() {
		return tableMap;
	}

	public void setTableMap(Map<String, String> tableMap) {
		this.tableMap = tableMap;
	}
	
	
}
