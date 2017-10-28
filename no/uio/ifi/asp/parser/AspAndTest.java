package no.uio.ifi.asp.parser;


import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

import java.util.ArrayList;

import static no.uio.ifi.asp.scanner.TokenKind.andToken;

public class AspAndTest extends AspSyntax{
//test

    AspAndTest(int n) {
        super(n);
    }

    ArrayList<AspNotTest> notTests = new ArrayList<>();

   public static AspAndTest parse(Scanner s) {
        Main.log.enterParser("and test");
        AspAndTest aat = new AspAndTest(s.curLineNum());
        while (true) {

            aat.notTests.add(AspNotTest.parse(s));
            if (s.curToken().kind != andToken) break;
            skip(s, andToken);
        }
        Main.log.leaveParser("and test");
        return aat;
   }

    @Override
    public void prettyPrint() {
        int nPrinted = 0;
        for (AspNotTest ant: notTests) {
            if (nPrinted > 0)
                Main.log.prettyWrite(" and ");
            ant.prettyPrint(); ++nPrinted;
        }
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue v = notTests.get(0).eval(curScope);
        for (int i = 1; i < notTests.size(); ++i) {
             if (! v.getBoolValue("and operand",this))
                 return v;
             v = notTests.get(i).eval(curScope);
             }
        return v;
   }
}
