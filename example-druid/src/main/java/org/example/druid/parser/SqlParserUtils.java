package org.example.druid.parser;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.clickhouse.parser.ClickhouseStatementParser;
import com.alibaba.druid.sql.dialect.hive.parser.HiveStatementParser;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.sql.visitor.SchemaStatVisitor;
import com.alibaba.druid.stat.TableStat;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * sql解析工具类
 */
public final class SqlParserUtils {

    /**
     * 获取sql所有表信息
     * @param sql
     * @param dbType
     * @return
     */
    public static Map<TableStat.Name, TableStat> getAllTableNameBySQL(String sql, DbType dbType) {
        SQLStatementParser parser = createParser(sql, dbType);
        SQLStatement sqlStatement = parser.parseStatement();
        SchemaStatVisitor visitor = SQLUtils.createSchemaStatVisitor(dbType);
        sqlStatement.accept(visitor);
        return visitor.getTables();
    }

    /**
     * 获取sql所有字段信息
     * @param sql
     * @param dbType
     * @return
     */
    public static Collection<TableStat.Column> getAllTableColumnNameBySQL(String sql, DbType dbType) {
        SQLStatementParser parser = createParser(sql, dbType);
        SQLStatement sqlStatement = parser.parseStatement();
        SchemaStatVisitor visitor = SQLUtils.createSchemaStatVisitor(dbType);
        sqlStatement.accept(visitor);
        return visitor.getColumns();
    }

    /**
     * 获取sql操作类型
     * @param sql
     * @param dbType
     * @return
     */
    public static Optional<TableStat> getSQLStat(String sql, DbType dbType) {
        SQLStatementParser parser = createParser(sql, dbType);
        SQLStatement sqlStatement = parser.parseStatement();
        SchemaStatVisitor visitor = SQLUtils.createSchemaStatVisitor(dbType);
        sqlStatement.accept(visitor);
        // 由于tables = LinkedHashMap有序类型，故可以获取第一个k,v对获得该sql类型
        Map<TableStat.Name, TableStat> tables = visitor.getTables();
        return tables.values().stream().findFirst();
    }

    /**
     * 根据引擎类型返回对应Parser
     * @param sql
     * @param dbType
     * @return
     */
    public static SQLStatementParser createParser(String sql, DbType dbType) {
        switch (dbType) {
            case mysql:
                return new MySqlStatementParser(sql);
            case hive:
                return new HiveStatementParser(sql);
            case clickhouse:
                return new ClickhouseStatementParser(sql);
            default:
                throw new IllegalArgumentException("Unsupported database type: " + dbType);
        }
    }

}
