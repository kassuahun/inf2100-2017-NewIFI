package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

import java.util.ArrayList;

import static no.uio.ifi.asp.scanner.TokenKind.*;


public class AspIfStmt extends AspStmt {

    ArrayList<AspExpr> condList = new ArrayList<AspExpr>();
    ArrayList<AspSuite> suteList = new ArrayList<AspSuite>();
    AspSuite elsesute = null;


    public AspIfStmt(int i) {
        super(i);
    }

    public static AspIfStmt parse(Scanner s) {
        Main.log.enterParser("if stmt");
        AspIfStmt ifStmt = new AspIfStmt(s.curLineNum());

        skip(s, ifToken);

        while (true){
            ifStmt.condList.add(AspExpr.parse(s));
            skip(s,colonToken);
            ifStmt.suteList.add(AspSuite.parse(s));
            if(s.curToken().kind != elifToken) break;
            skip(s, elifToken);
        }
        if (s.curToken().kind == elseToken)
        {
            skip(s, elseToken);
            skip(s,colonToken);
            //ifStmt.suteList.add(AspSuite.parse(s));
            ifStmt.elsesute = AspSuite.parse(s);
        }

        Main.log.leaveParser("if stmt");
        return ifStmt;
    }

    @Override
    public void prettyPrint() {
        Main.log.prettyWrite(ifToken.toString() +" ");

        condList.get(0).prettyPrint();
        Main.log.prettyWrite(colonToken.toString());
        suteList.get(0).prettyPrint();
        for (int i = 1; i < condList.size() ; i++) {
            Main.log.prettyWrite(elifToken.toString() +" ");
            condList.get(i).prettyPrint();
            Main.log.prettyWrite(colonToken.toString());
            suteList.get(i).prettyPrint();
        }
        if (elsesute != null){

            Main.log.prettyWrite(elseToken.toString() +"");
            Main.log.prettyWrite(colonToken.toString());
            elsesute.prettyPrint();
        }


    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
