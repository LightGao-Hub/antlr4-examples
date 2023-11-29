package org.example.druid.parser;

import java.util.*;
import java.util.stream.Collectors;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.stat.TableStat;
import com.alibaba.druid.stat.TableStat.Name;
import com.alibaba.druid.stat.TableStat.Column;
import com.alibaba.druid.sql.visitor.SchemaStatVisitor;
import org.junit.Test;

/**
 * MySqlSchemaStatVisitor 快捷函数：
 * 数据库类型：getDbType());
 * 查询的字段：getColumns());
 * 表名：getTables().keySet());
 * 条件：visitor.getConditions());
 * group by：visitor.getGroupByColumns());
 * order by：getOrderByColumns());
 */
public class Example {

    @Test
    public void demoParser() {
        // 复杂嵌套sql
        String sql = "SELECT ID,NAME,AGE FROM STUDENT JOIN (SELECT ID FROM (SELECT A.ID,B.ID AS BI FROM A JOIN B ON A.ID=B.ID)) AS C ON STUDENT.ID = C.ID";
        getAllTableNameBySQL(sql).forEach(System.out::println);
        System.out.println("---------------------------------");
        getAllTableColumnNameBySQL(sql).forEach(System.out::println);
    }

    // 获取所有table[不含别名]
    public static List<String> getAllTableNameBySQL(String sql) {
        SQLStatementParser parser = new MySqlStatementParser(sql);
        SQLStatement sqlStatement = parser.parseStatement();
        SchemaStatVisitor visitor = SQLUtils.createSchemaStatVisitor(DbType.mysql);
        sqlStatement.accept(visitor);
        Map<Name, TableStat> tables = visitor.getTables();
        return tables.keySet().stream().map(Name::getName).collect(Collectors.toList());
    }

    // 获取所有字段名[不含别名]
    public static List<String> getAllTableColumnNameBySQL(String sql) {
        SQLStatementParser parser = new MySqlStatementParser(sql);
        SQLStatement sqlStatement = parser.parseStatement();
        SchemaStatVisitor visitor = SQLUtils.createSchemaStatVisitor(DbType.mysql);
        sqlStatement.accept(visitor);
        Collection<Column> columns = visitor.getColumns();
        return columns.stream().map(Column::getName).collect(Collectors.toList());
    }

}
