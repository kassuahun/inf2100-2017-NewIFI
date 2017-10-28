package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

import static no.uio.ifi.asp.scanner.TokenKind.notToken;

public class AspNotTest extends AspSyntax{
    TokenKind tKind=null;
    AspComparison comp;

    AspNotTest(int n) {
        super(n);
    }

    public static AspNotTest parse(Scanner s) {
        Main.log.enterParser("not test");
        AspNotTest notTest = new AspNotTest(s.curLineNum());

        if (s.curToken().kind == notToken){

            notTest.tKind = notToken;
            skip(s, notToken);
        }
        notTest.comp = AspComparison.parse(s);

        Main.log.leaveParser("not test");
        return notTest;
    }

    @Override
    protected void prettyPrint() {
        if (tKind!=null) Main.log.prettyWrite(notToken.toString()+" ");

        comp.prettyPrint();

    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {

        RuntimeValue v = comp.eval(curScope);
        if (tKind!=null) {
            v = v.evalNot(this);
        }
        return v;
    }

}
