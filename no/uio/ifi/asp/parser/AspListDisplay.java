package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeListValue;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

import java.util.ArrayList;

import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspListDisplay extends AspAtom {

    ArrayList<AspExpr> exprList = new ArrayList<AspExpr>();

    AspListDisplay(int n) {
        super(n);
    }

    public static AspListDisplay parse(Scanner s) {
        Main.log.enterParser("list display");
        AspListDisplay listDisp = new AspListDisplay(s.curLineNum());

        if(s.curToken().kind == leftBracketToken) {
            skip(s,leftBracketToken);
            if(s.curToken().kind != rightBracketToken){
                while(true) {
                    listDisp.exprList.add(AspExpr.parse(s));
                    if(s.curToken().kind != commaToken) break;
                    skip(s,commaToken);
                }
            }
            skip(s,rightBracketToken);
        }
        Main.log.leaveParser("list display");
        return listDisp;
    }

    @Override
    protected void prettyPrint() {
       boolean in = false;
        Main.log.prettyWrite(leftBracketToken.toString());
        if(exprList.size()>0){
            exprList.get(0).prettyPrint();
            for (int i = 1; i < exprList.size(); i++) {
                Main.log.prettyWrite(commaToken.toString()+" ");
                exprList.get(i).prettyPrint();
            }
        }
        Main.log.prettyWrite(rightBracketToken.toString());

    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        ArrayList<RuntimeValue> valList = new ArrayList<>();
        for (int i = 0; i < exprList.size() ; i++) {
            RuntimeValue rtValue = exprList.get(i).eval(curScope);
            valList.add(rtValue);
        }

        RuntimeListValue rtListVal = new RuntimeListValue(valList);

        return rtListVal;
    }
}
