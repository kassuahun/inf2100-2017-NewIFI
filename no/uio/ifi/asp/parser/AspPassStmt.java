package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeNoneValue;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

import static no.uio.ifi.asp.scanner.TokenKind.newLineToken;
import static no.uio.ifi.asp.scanner.TokenKind.passToken;

/**
 *
 */
public class AspPassStmt extends AspStmt {
    public AspPassStmt(int i) {
        super(i);
    }

    public static AspPassStmt parse(Scanner s) {
        Main.log.enterParser("pass stmt");
        skip(s, passToken);
        skip(s, newLineToken);
        Main.log.leaveParser("pass stmt");
        return null;
    }


    @Override
    public void prettyPrint() {
        Main.log.prettyWriteLn(passToken.toString());
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return new RuntimeNoneValue();
    }
}