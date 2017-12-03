package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

/**
 *
 */
public class AspTermOpr extends AspSyntax {
    TokenKind tOprKind;

    AspTermOpr(int n) {
        super(n);
    }

    public static AspTermOpr parse(Scanner s) {
        Main.log.enterParser("term opr");
        AspTermOpr tOpr = new AspTermOpr(s.curLineNum());

        tOpr.tOprKind = s.curToken().kind;
        skip(s,tOpr.tOprKind);

       Main.log.leaveParser("term opr");
        return tOpr;
    }

    @Override
    protected void prettyPrint() {
        Main.log.prettyWrite(" "+tOprKind.toString() + " ");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
