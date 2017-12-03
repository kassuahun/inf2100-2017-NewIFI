package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

import static no.uio.ifi.asp.scanner.TokenKind.newLineToken;

/**
 *
 */
public class AspExprStmt extends AspStmt{
    AspExpr expr;

    public AspExprStmt(int i) {
        super(i);
    }

    public static AspExprStmt parse(Scanner s) {
        Main.log.enterParser("expr stmt");
        AspExprStmt exprStmt = new AspExprStmt(s.curLineNum());
        exprStmt.expr = AspExpr.parse(s);
        skip(s, newLineToken);
        Main.log.leaveParser("expr stmt");
        return exprStmt;
    }

    @Override
    public void prettyPrint() {
        //-- Must be changed in part 2:
        expr.prettyPrint();
        Main.log.prettyWriteLn();
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        RuntimeValue rtVal = expr.eval(curScope);
        return rtVal;
    }
}
