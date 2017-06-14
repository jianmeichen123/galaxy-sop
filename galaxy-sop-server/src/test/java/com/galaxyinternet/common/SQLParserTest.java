package com.galaxyinternet.common;

import java.util.List;

import org.junit.Test;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.deparser.ExpressionDeParser;
import net.sf.jsqlparser.util.deparser.SelectDeParser;

public class SQLParserTest {
	@Test
	public void testParse() throws Exception {

		//String sql = "select * from t_1 t1,t_2 t2";
		String sql = "select * from t_1  inner join t_2 ";
		Select select = (Select) CCJSqlParserUtil.parse(sql);

		// Start of value modification
		StringBuilder buffer = new StringBuilder();
		ExpressionDeParser expressionDeParser = new ExpressionDeParser();
		SelectDeParser deparser = new MySelectDeParser(expressionDeParser, buffer);
		expressionDeParser.setSelectVisitor(deparser);
		expressionDeParser.setBuffer(buffer);
		select.getSelectBody().accept(deparser);
		// End of value modification

		System.out.println(buffer.toString());
	}

}

class MySelectDeParser extends SelectDeParser {

	public MySelectDeParser(ExpressionVisitor expressionVisitor, StringBuilder buffer) {
        super(expressionVisitor, buffer);
    }
	
	/*
	@Override
	public void visit(PlainSelect plainSelect) {
		super.visit(plainSelect);
		FromItem item = plainSelect.getFromItem();
		List<Join> joins = plainSelect.getJoins();
		
		Table table = (Table)item;
		String alias = table.getAlias() != null ? table.getAlias().getName() : table.getFullyQualifiedName();
		System.out.println(table.getFullyQualifiedName()+"   "+alias);
		for(Join join :joins)
		{
			table = (Table)join.getRightItem();
			alias = table.getAlias() != null ? table.getAlias().getName() : table.getFullyQualifiedName();
			System.out.println(table.getFullyQualifiedName()+"   "+alias);
		}
		Expression where = plainSelect.getWhere();
		if(where == null)
		{
			this.getBuffer().append(" where 2=3");
		}
		this.getBuffer().append(" and 1=2");
	}*/


	@Override
	public void visit(Table tableName) {
		System.out.println(tableName.getFullyQualifiedName()+"   "+tableName.getAlias());
		super.visit(tableName);
	}
	
	


	
	
	
	
	

}
