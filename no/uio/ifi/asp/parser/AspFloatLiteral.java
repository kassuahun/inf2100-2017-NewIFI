package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeFloatValue;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

import java.text.DecimalFormat;

public class AspFloatLiteral extends AspAtom{
    double fLit;
    AspFloatLiteral(int n) {
        super(n);
    }

    public static AspFloatLiteral parse(Scanner s) {
        Main.log.enterParser("float literal");
        AspFloatLiteral floatLiteral = new AspFloatLiteral(s.curLineNum());

        if(s.curToken().kind == TokenKind.floatToken){
            floatLiteral.fLit= s.curToken().floatLit;
        }

        skip(s, TokenKind.floatToken);


        Main.log.leaveParser("float literal");
        return floatLiteral;
    }


    @Override
    protected void prettyPrint() {
        DecimalFormat df = new DecimalFormat("0.000000");
        Main.log.prettyWrite(""+df.format(fLit));
        //String .format("%.6g%n", fLit);

       // Main.log.prettyWrite(String .format("%.6g", fLit));

    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return new RuntimeFloatValue(fLit);
    }
}
