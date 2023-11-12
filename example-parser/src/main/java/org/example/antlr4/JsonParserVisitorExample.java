package org.example.antlr4;

import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

public class JsonParserVisitorExample implements JsonParserVisitor<Integer> {

	@Override
	public Integer visitJson(JsonParserParser.JsonContext ctx) {
		List<JsonParserParser.ValueContext> value = ctx.value();
		return value.stream().mapToInt(this::visitValue).sum();
	}

	@Override
	public Integer visitValue(JsonParserParser.ValueContext ctx) {
		if (ctx.json() != null) {
			return visitJson(ctx.json());
		}
		if (ctx.INT() != null) {
			return Integer.parseInt(ctx.INT().getText());
		}
		return 0;
	}

	@Override
	public Integer visit(ParseTree parseTree) {
		return null;
	}

	@Override
	public Integer visitChildren(RuleNode ruleNode) {
		return null;
	}

	@Override
	public Integer visitTerminal(TerminalNode terminalNode) {
		return null;
	}

	@Override
	public Integer visitErrorNode(ErrorNode errorNode) {
		return null;
	}
}