package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeStringValue;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspStringLiteral extends AspAtom{
    String str;

    AspStringLiteral(int n) {
        super(n);
    }

    /**
     * The scanner class handels both "" and '' cases and create the tokken accordingly
     * @param "Scanner" object
     * @return Asp String Literal Object
     */
    public static AspStringLiteral parse(Scanner s) {
        Main.log.enterParser("string literal");
        AspStringLiteral strLit = new AspStringLiteral(s.curLineNum());

        strLit.str= s.curToken().stringLit;
        skip(s, TokenKind.stringToken);

        Main.log.leaveParser("string literal");
        return strLit;
    }

    @Override
    protected void prettyPrint() {
        Main.log.prettyWrite("\""+str+ "\"");

    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue rtVal = new RuntimeStringValue(str);
        return rtVal;
    }
}
