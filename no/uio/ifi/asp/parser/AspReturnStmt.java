package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

import static no.uio.ifi.asp.scanner.TokenKind.newLineToken;
import static no.uio.ifi.asp.scanner.TokenKind.returnToken;

/**
 *
 */
public class AspReturnStmt extends AspStmt{
    AspExpr expr;

    public AspReturnStmt(int i) {
        super(i);
    }

    public static AspReturnStmt parse(Scanner s) {
        Main.log.enterParser("return stmt");
        AspReturnStmt rStmt = new AspReturnStmt(s.curLineNum());

        skip(s, returnToken);
        rStmt.expr=AspExpr.parse(s);
        skip(s,newLineToken);
        Main.log.leaveParser("return stmt");
        return rStmt;

    }
    @Override
    public void prettyPrint() {
        Main.log.prettyWrite(returnToken.toString() + " ");
        expr.prettyPrint();
        Main.log.prettyWriteLn();
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        RuntimeValue v = expr.eval(curScope);
        throw new RuntimeReturnValue(v);
        //return v;
    }
}




