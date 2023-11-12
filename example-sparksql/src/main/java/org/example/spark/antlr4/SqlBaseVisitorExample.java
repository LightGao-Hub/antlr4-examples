package org.example.spark.antlr4;

public class SqlBaseVisitorExample extends SqlBaseBaseVisitor<String> {
    @Override
    public String visitSingleStatement(SqlBaseParser.SingleStatementContext ctx) {
        System.out.println(" ...SqlBaseVisitorExample... "); // 打印
        return visitChildren(ctx);
    }
}
