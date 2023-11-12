package org.example.spark.antlr4;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Test;

public class Example {

    @Test
    public void demoVisitor() {
        String query = "SELECT * FROM STUDENT WHERE ID > 10;";
        SqlBaseLexer lexer = new SqlBaseLexer(new ANTLRInputStream(query.toUpperCase()));
        SqlBaseParser parser = new SqlBaseParser(new CommonTokenStream(lexer));

        // 创建自定义访问器
        SqlBaseVisitorExample visitor = new SqlBaseVisitorExample();
        // 将parser语法树头节点放入
        visitor.visitSingleStatement(parser.singleStatement());
    }

}
