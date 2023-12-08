package org.example.druid.parser;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.dialect.hive.parser.HiveStatementParser;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.sql.visitor.SchemaStatVisitor;
import com.alibaba.druid.stat.TableStat;
import com.alibaba.druid.stat.TableStat.Column;
import com.alibaba.druid.stat.TableStat.Name;

import org.junit.Test;

import java.util.Collection;
import java.util.Map;

/**
 * 快捷函数：
 * 数据库类型：getDbType());
 * 查询的字段：getColumns());
 * 表名：getTables().keySet());
 * 条件：visitor.getConditions());
 * group by：visitor.getGroupByColumns());
 * order by：getOrderByColumns());
 */
public class HiveParserExample {

    private final DbType dbType = DbType.hive;

    @Test
    public void demoHiveCreateParser() {
        String sql = "CREATE EXTERNAL TABLE dwd_database.table_name( \n" +
                "id                BIGINT,\n" +
                "user_id           STRING,\n" +
                "gmt_modified      TIMESTAMP,\n" +
                "gmt_create        TIMESTAMP,\n" +
                "pending_reward    INT,\n" +
                "description       STRING\n" +
                ")\n" +
                "PARTITIONED BY ( \n" +
                "pt STRING \n" +
                ")\n" +
                "row format delimited fields terminated by '\\t'\n" +
                "STORED AS TEXTFILE\n" +
                "location 'hdfs://nameservice1/user/hive/warehouse/dwd_database.db/table_name';";
        getAllTableNameBySQL(sql);
        System.out.println("---------------------------------");
        getAllTableColumnNameBySQL(sql);
    }

    @Test
    public void demoHiveCreateParser2() {
        String sql = "create table table_name\n" +
                "        as" +
                "       select *  from t_table_name where pt='20210829';";
        getAllTableNameBySQL(sql);
        System.out.println("---------------------------------");
        getAllTableColumnNameBySQL(sql);
    }

    @Test
    public void demoHiveSelectParser2() {
        String sql = "SELECT p.id, p.name, n.details\n" +
                "FROM partition_table p\n" +
                "JOIN non_partition_table n ON p.id = n.id\n" +
                "WHERE p.partition_col = '2023-12-01' -- 分区条件\n" +
                "HAVING COUNT(n.id) > 1; -- HAVING 子句，这里以 n.id 的数量大于 1 为例\n";
        getAllTableNameBySQL(sql);
        System.out.println("---------------------------------");
        getAllTableColumnNameBySQL(sql);
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

}
