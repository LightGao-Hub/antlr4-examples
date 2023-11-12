package org.example.antlr4;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;

public class Example {

    @Test
    public void demoParser() {

        ANTLRInputStream input = new ANTLRInputStream("{1,2,{3,4}}");
        //词法解析器，处理input
        JsonParserLexer lexer = new JsonParserLexer(input);
        //词法符号的缓冲器，存储词法分析器生成的词法符号
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        //语法分析器，处理词法符号缓冲区的内容
        JsonParserParser parser = new JsonParserParser(tokens);

        ParseTree tree = parser.json();
        System.out.println(tree.toStringTree(parser));
    }

    @Test
    public void demoListener(){

        ANTLRInputStream input = new ANTLRInputStream("{1,2,{3,4},{3,4}}");
        //词法解析器，处理input
        JsonParserLexer lexer = new JsonParserLexer(input);
        //词法符号的缓冲器，存储词法分析器生成的词法符号
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        //语法分析器，处理词法符号缓冲区的内容
        JsonParserParser parser = new JsonParserParser(tokens);

        ParseTree tree = parser.json();
        // ParseTreeWalker类将实现的MeSqlParserBaseListener监听器放入
        new ParseTreeWalker().walk(new JsonParserListenerExample(), tree);
    }

    @Test
    public void demoVisitor() {

        ANTLRInputStream input = new ANTLRInputStream("{1,2,{3,4},{3,4}}");
        //词法解析器，处理input
        JsonParserLexer lexer = new JsonParserLexer(input);
        //词法符号的缓冲器，存储词法分析器生成的词法符号
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        //语法分析器，处理词法符号缓冲区的内容
        JsonParserParser parser = new JsonParserParser(tokens);

        JsonParserVisitorExample jsonParserVisitorExample = new JsonParserVisitorExample();
        Integer sum = jsonParserVisitorExample.visitJson(parser.json());
        System.out.println(sum);
    }

}
