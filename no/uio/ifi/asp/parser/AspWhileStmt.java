package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

import static no.uio.ifi.asp.scanner.TokenKind.colonToken;
import static no.uio.ifi.asp.scanner.TokenKind.whileToken;

/**
 *
 */

public class AspWhileStmt extends AspStmt{
    AspExpr condition;
    AspSuite boady;

    public AspWhileStmt(int i) {
        super(i);
    }

    public static AspWhileStmt parse(Scanner s) {
        Main.log.enterParser("while stmt");

        AspWhileStmt ws = new AspWhileStmt(s.curLineNum());
        skip(s, whileToken);
        ws.condition=AspExpr.parse(s);
        skip(s, colonToken);
        ws.boady = AspSuite.parse(s);

        Main.log.leaveParser("while stmt");
        return ws;

    }

    @Override
    public void prettyPrint() {
        Main.log.prettyWrite(whileToken.toString()+ " ");
        condition.prettyPrint();
        Main.log.prettyWrite(colonToken.toString());
        boady.prettyPrint();
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue rtv = null;

        while(condition.eval(curScope).getBoolValue("while test", this)){
            boady.eval(curScope);
        }
        condition.eval(curScope);

        return rtv;
    }
}




