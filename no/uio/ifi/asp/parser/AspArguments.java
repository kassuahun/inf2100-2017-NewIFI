/*
* This program reads from the scanner and
*
 */


package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeListValue;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

import java.util.ArrayList;

import static no.uio.ifi.asp.scanner.TokenKind.*;
/**
*This class creates the object of AspArguments and stores the Asp expressions in ArrayList.
* @ extends AspPrimarySuffix
*/


public class AspArguments extends AspPrimarySuffix {

    ArrayList<AspExpr> exprList = new ArrayList<AspExpr>();

    AspArguments(int n) {
        super(n);
    }


    public static AspArguments parse(Scanner s) {
        Main.log.enterParser("arguments");
        AspArguments arg = new AspArguments(s.curLineNum());


        if(s.curToken().kind == leftParToken) {
            skip(s,leftParToken);
            if(s.curToken().kind != rightParToken){
                while(true) {
                    arg.exprList.add(AspExpr.parse(s));

                    if(s.curToken().kind == commaToken)
                    {
                        skip(s,commaToken);
                    }
                    else  break;
                }
            }
            skip(s,rightParToken);
        }
        Main.log.leaveParser("arguments");
        return arg;
    }
    @Override
    protected void prettyPrint() {
        boolean in = false;
        Main.log.prettyWrite(leftParToken.toString());
        if(exprList.size()>0){
            exprList.get(0).prettyPrint();
            for (int i = 1; i < exprList.size(); i++) {
                Main.log.prettyWrite(commaToken.toString()+" ");
                exprList.get(i).prettyPrint();
            }
        }
        Main.log.prettyWrite(rightParToken.toString());

    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        ArrayList<RuntimeValue> argParams = new ArrayList<>();
        for (AspExpr anExprList : exprList) {
            argParams.add(anExprList.eval(curScope));
        }

        return new RuntimeListValue(argParams);
    }
}
