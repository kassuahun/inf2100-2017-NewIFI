package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeLibrary;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

import java.util.ArrayList;

import static no.uio.ifi.asp.scanner.TokenKind.eofToken;

public class AspProgram extends AspSyntax {
    //-- Must be changed in part 2:
    ArrayList<AspStmt> stmts = new ArrayList<>();

    AspProgram(int n) {
	super(n);
    }


    public static AspProgram parse(Scanner s) {
	Main.log.enterParser("program");

	AspProgram prog = new AspProgram(s.curLineNum());
	while (s.curToken().kind != eofToken) {
	    //-- Must be changed in part 2:

         prog.stmts.add(AspStmt.parse(s));
         //s.readNextToken();

	}
	skip(s, eofToken);

	Main.log.leaveParser("program");
	return prog;
    }


    @Override
    public void prettyPrint() {
	//-- Must be changed in part 2:
        for (AspStmt st: stmts) {
            st.prettyPrint();
        }
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
	//-- Must be changed in part 4:
        RuntimeLibrary lScope = new RuntimeLibrary();
        RuntimeScope scope = new RuntimeScope(lScope);
        for (AspStmt stmt : stmts) {
            stmt.eval(scope);

        }
        return null;
    }
}
