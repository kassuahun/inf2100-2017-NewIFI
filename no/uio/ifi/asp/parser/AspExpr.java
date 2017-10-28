package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

import java.util.ArrayList;

import static no.uio.ifi.asp.scanner.TokenKind.orToken;

public class AspExpr extends AspSyntax {
    //-- Must be changed in part 2:
    ArrayList<AspAndTest> andTests = new ArrayList<>();

    AspExpr(int n) {
	super(n);
    }


    public static AspExpr parse(Scanner s) {
	Main.log.enterParser("expr");

	//-- Must be changed in part 2:
	AspExpr expr = new AspExpr(s.curLineNum());
        while (true) {
            expr.andTests.add(AspAndTest.parse(s));
            if (s.curToken().kind != orToken) break;
            skip(s, orToken);
        }

	Main.log.leaveParser("expr");
	return expr;
    }


    @Override
    public void prettyPrint() {
	//-- Must be changed in part 2:
        andTests.get(0).prettyPrint();
        for (int i = 1; i < andTests.size() ; i++) {
            Main.log.prettyWrite(" "+orToken.toString()+" ");
            andTests.get(i).prettyPrint();
        }

    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
	//-- Must be changed in part 3:



	return null;
    }
}
