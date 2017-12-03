package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

import static no.uio.ifi.asp.scanner.TokenKind.leftBracketToken;
import static no.uio.ifi.asp.scanner.TokenKind.rightBracketToken;

/**
 *
 */
public class AspSubscription extends AspPrimarySuffix{

    AspSubscription(int n) {
        super(n);
    }

    AspExpr expr;

    public static AspSubscription parse(Scanner s) {
        Main.log.enterParser("subscription");
        AspSubscription sub = new AspSubscription(s.curLineNum());

        skip(s, leftBracketToken);
        sub.expr = AspExpr.parse(s);
        skip(s, rightBracketToken);

        Main.log.leaveParser("subscription");
        return sub;
    }
    @Override
    public void prettyPrint() {
        Main.log.prettyWrite(leftBracketToken.toString());
        expr.prettyPrint();
        Main.log.prettyWrite(rightBracketToken.toString());
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        RuntimeValue v = expr.eval(curScope);
        return v;
    }

}
