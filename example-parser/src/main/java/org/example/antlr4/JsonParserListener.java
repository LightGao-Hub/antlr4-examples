// Generated from D:/IdeaProjects/antlr4-examples/example-parser/src/test/resources/JsonParser.g4 by ANTLR 4.13.1
package org.example.antlr4;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link JsonParserParser}.
 */
public interface JsonParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link JsonParserParser#json}.
	 * @param ctx the parse tree
	 */
	void enterJson(JsonParserParser.JsonContext ctx);
	/**
	 * Exit a parse tree produced by {@link JsonParserParser#json}.
	 * @param ctx the parse tree
	 */
	void exitJson(JsonParserParser.JsonContext ctx);
	/**
	 * Enter a parse tree produced by {@link JsonParserParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(JsonParserParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link JsonParserParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(JsonParserParser.ValueContext ctx);
}