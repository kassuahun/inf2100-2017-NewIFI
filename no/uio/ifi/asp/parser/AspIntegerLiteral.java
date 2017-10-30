package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeIntValue;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspIntegerLiteral extends AspAtom{
    long iLit;

    AspIntegerLiteral(int n) {
        super(n);
    }

    public static AspIntegerLiteral parse(Scanner s) {
        Main.log.enterParser("integer literal");
        AspIntegerLiteral intLit = new AspIntegerLiteral(s.curLineNum());

        if(s.curToken().kind == TokenKind.integerToken){
            intLit.iLit= s.curToken().integerLit;
        }

        skip(s, TokenKind.integerToken);

        Main.log.leaveParser("integer literal");
        return intLit;
    }

    @Override
    protected void prettyPrint() { Main.log.prettyWrite(""+ iLit + "");

    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue val = new RuntimeIntValue(iLit);
        return val;
    }
}
