package org.example.druid.parser;

import java.util.*;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLSelectQuery;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.sql.visitor.SchemaStatVisitor;
import com.alibaba.druid.stat.TableStat;
import com.alibaba.druid.stat.TableStat.Name;
import com.alibaba.druid.stat.TableStat.Column;

import org.junit.Test;


public class MysqlParserExample {

    private final DbType dbType = DbType.mysql;

    @Test
    public void parserTables() {
        // 复杂嵌套sql
        String sql = "SELECT ID,NAME,AGE FROM DBA.STUDENT JOIN (\n" +
                "SELECT ID FROM (\n" +
                "SELECT A.ID,B.ID AS BI FROM DBA.A JOIN DBA.B ON A.ID=B.ID)) AS C ON STUDENT.ID=C.ID";
        SQLStatementParser parser = new MySqlStatementParser(sql);
        SQLStatement sqlStatement = parser.parseStatement();
        SchemaStatVisitor visitor = SQLUtils.createSchemaStatVisitor(dbType);
        sqlStatement.accept(visitor);
        Map<Name, TableStat> tables = visitor.getTables();
        for (Map.Entry<TableStat.Name, TableStat> tableStatEntry : tables.entrySet()) {
            System.out.println("表名：" + tableStatEntry.getKey().getName());
            System.out.println("操作名：" + tableStatEntry.getValue());
        }
    }

    @Test
    public void parserColumns() {
        String sql = "CREATE TABLE users (\n" +
                "    user_id INT AUTO_INCREMENT PRIMARY KEY,\n" +
                "    username VARCHAR(50) NOT NULL,\n" +
                "    email VARCHAR(100) NOT NULL,\n" +
                "    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP\n" +
                ");\n";
        SQLStatementParser parser = new MySqlStatementParser(sql);
        SQLStatement sqlStatement = parser.parseStatement();
        SchemaStatVisitor visitor = SQLUtils.createSchemaStatVisitor(dbType);
        sqlStatement.accept(visitor);
        Collection<Column> columns = visitor.getColumns();
        for (Column column : columns) {
            System.out.println("字段：" + column.getName());
            System.out.println("字段类型：" + column.getDataType());
            System.out.println("表名：" + column.getTable());
        }
    }

    @Test
    public void parserSelect() {
        String sql = "SELECT ID,NAME,AGE FROM DBA.STUDENT WHERE ID >= 10 GROUP BY NAME HAVING AGE >= 18 LIMIT 10;";
        SQLStatementParser parser = new MySqlStatementParser(sql);
        SQLSelectStatement sqlStatement = (SQLSelectStatement) parser.parseStatement();
        SchemaStatVisitor visitor = SQLUtils.createSchemaStatVisitor(dbType);
        sqlStatement.accept(visitor);
        SQLSelectQuery query = sqlStatement.getSelect().getQuery();
        System.out.println(query);
    }


    @Test
    public void demoMysqlSelectParser() {
        // 复杂嵌套sql
        String sql = "SELECT ID,NAME,AGE FROM DBA.STUDENT JOIN (\n" +
                "SELECT ID FROM (\n" +
                "SELECT A.ID,B.ID AS BI FROM DBA.A JOIN DBA.B ON A.ID=B.ID)) AS C ON STUDENT.ID=C.ID";
        getAllTableNameBySQL(sql);
        System.out.println("---------------------------------");
        getAllTableColumnNameBySQL(sql);
    }

    @Test
    public void demoMysqlWithParser() {
        // 复杂嵌套sql
        String sql = "WITH a AS ( SELECT * FROM category WHERE cname = '家电' ),\n" +
                "b AS ( SELECT * FROM products WHERE pname IN ( '小米电视机', '格力空调' ) ) \n" +
                "SELECT * FROM\ta\tLEFT JOIN b ON a.cid = b.category_id;";
        getAllTableNameBySQL(sql);
        System.out.println("---------------------------------");
        getAllTableColumnNameBySQL(sql);
    }

    @Test
    public void demoMysqlUseParser() {
        // 复杂嵌套sql
        String sql = "insert into tenant_db_qifutech.general_cas_db_sys_resource SELECT * from tenant_db_qifutech.general_cas_db_sys_resource";
        getAllTableNameBySQL(sql);
        System.out.println("---------------------------------");
        getAllTableColumnNameBySQL(sql);
    }

    @Test
    public void demoMysqlCreateParser() {
        String sql = "CREATE TABLE users (\n" +
                "    user_id INT AUTO_INCREMENT PRIMARY KEY,\n" +
                "    username VARCHAR(50) NOT NULL,\n" +
                "    email VARCHAR(100) NOT NULL,\n" +
                "    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP\n" +
                ");\n";
        getAllTableNameBySQL(sql);
        System.out.println("---------------------------------");
        getAllTableColumnNameBySQL(sql);
    }

    @Test
    public void demoMysqlDropParser() {
        String sql = "DROP TABLE IF EXISTS users;";
        getAllTableNameBySQL(sql);
        System.out.println("---------------------------------");
        getAllTableColumnNameBySQL(sql);
    }

    @Test
    public void demoMysqlInsertParser() {
        // 复杂嵌套sql
        String sql = "INSERT INTO users (username, email, created_at)\n" +
                "VALUES ('JohnDoe', 'johndoe@example.com', NOW());";
        getAllTableNameBySQL(sql);
        System.out.println("---------------------------------");
        getAllTableColumnNameBySQL(sql);
    }

    @Test
    public void demoGetSQLStat() {
        // 复杂嵌套sql
        String sql = "INSERT INTO users (username, email, created_at)\n" +
                "VALUES ('JohnDoe', 'johndoe@example.com', NOW());";
        getSQLStat(sql);
    }

    @Test
    public void demoFormatSQLServer() {
        // mysql limit 语句与 SQLServer 语法不同
        String sql = "SELECT ID,NAME,AGE FROM DBA.STUDENT LIMIT 10";
        formatSQLServer(sql);
    }

    @Test
    public void formatOracle() {
        // mysql substring 语句与 Oracle 语法不同
        String sql = "SELECT substring(\"aaa\",2,2) FROM my_table LIMIT 10;";
        formatOracle(sql);
    }

    // 获取所有table[不含别名]
    public void getAllTableNameBySQL(String sql) {
        Map<Name, TableStat> tables = SqlParserUtils.getAllTableNameBySQL(sql, dbType);
        for (Map.Entry<TableStat.Name, TableStat> tableStatEntry : tables.entrySet()) {
            System.out.println("表名：" + tableStatEntry.getKey().getName());
            System.out.println("操作名：" + tableStatEntry.getValue());
        }
    }

    // 获取所有字段名[不含别名]
    public void getAllTableColumnNameBySQL(String sql) {
        Collection<Column> columns = SqlParserUtils.getAllTableColumnNameBySQL(sql, dbType);
        for (Column column : columns) {
            System.out.println("字段：" + column.getName());
            System.out.println("字段类型：" + column.getDataType());
            System.out.println("表名：" + column.getTable());
        }
    }

    // 获取所有table[不含别名]
    public void getSQLStat(String sql) {
        Optional<TableStat> sqlStat = SqlParserUtils.getSQLStat(sql, dbType);
        System.out.println(sqlStat.get());
    }

    // sql转换
    public void formatSQLServer(String sql) {
        String sqlServer = SQLUtils.formatSQLServer(sql);
        System.out.println(sqlServer);
    }

    // sql转换
    public void formatOracle(String sql) {
        String oracle = SQLUtils.formatOracle(sql);
        System.out.println(oracle);
    }

}
