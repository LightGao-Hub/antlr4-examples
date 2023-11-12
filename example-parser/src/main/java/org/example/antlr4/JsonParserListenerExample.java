package org.example.antlr4;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.HashMap;
import java.util.Map;

public class JsonParserListenerExample implements JsonParserListener {

    Map<String, Integer> map = new HashMap<>();

    @Override
    public void enterJson(JsonParserParser.JsonContext ctx) {
        if (!map.containsKey(ctx.getText())) {
            map.put(ctx.getText(), 0);
        }
    }

    @Override
    public void exitJson(JsonParserParser.JsonContext ctx) {
        if (ctx.parent == null) {
            int sum = map.values().stream().mapToInt(i -> i).sum();
            System.out.println(" result = " + sum);
        }
    }

    @Override
    public void enterValue(JsonParserParser.ValueContext ctx) {
        if (ctx.INT() != null && map.containsKey(ctx.parent.getText())) {
            map.put(ctx.parent.getText(), map.get(ctx.parent.getText()) + Integer.parseInt(ctx.INT().getText()));
        }
    }

    @Override
    public void exitValue(JsonParserParser.ValueContext ctx) {

    }

    @Override
    public void visitTerminal(TerminalNode terminalNode) {

    }

    @Override
    public void visitErrorNode(ErrorNode errorNode) {

    }

    @Override
    public void enterEveryRule(ParserRuleContext parserRuleContext) {

    }

    @Override
    public void exitEveryRule(ParserRuleContext parserRuleContext) {

    }
}
