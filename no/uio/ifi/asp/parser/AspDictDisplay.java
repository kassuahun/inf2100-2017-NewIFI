package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeDictValue;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

import java.util.ArrayList;
import java.util.HashMap;

import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspDictDisplay extends AspAtom{
    ArrayList<AspStringLiteral> strLitList = new ArrayList<AspStringLiteral>();
    ArrayList<AspExpr> exprsList = new ArrayList<AspExpr>();

    AspDictDisplay(int n) {
        super(n);
    }

    public static AspDictDisplay parse(Scanner s) {
        Main.log.enterParser("dict display");
        AspDictDisplay dictDisplay = new AspDictDisplay(s.curLineNum());

        skip(s,leftBraceToken);
        if (s.curToken().kind != rightBraceToken)
        {
            while(true){
               dictDisplay.strLitList.add(AspStringLiteral.parse(s));
               skip(s,colonToken);
               dictDisplay.exprsList.add(AspExpr.parse(s));
               if (s.curToken().kind!=commaToken) break;
               skip(s,commaToken);
            }

        }
        skip(s, rightBraceToken);

        Main.log.leaveParser("dict display");
        return dictDisplay;
    }


    @Override
    protected void prettyPrint() {
        Main.log.prettyWrite(leftBraceToken.toString());
        if (strLitList.size()>0){
            strLitList.get(0).prettyPrint();
            Main.log.prettyWrite(colonToken.toString());
            exprsList.get(0).prettyPrint();
            if (strLitList.size()>1){
                for (int i = 1; i < strLitList.size() ; i++) {
                    Main.log.prettyWrite(commaToken.toString()+ " ");
                    strLitList.get(i).prettyPrint();
                    Main.log.prettyWrite(colonToken.toString());
                    exprsList.get(i).prettyPrint();
                }
            }
        }
        Main.log.prettyWrite(rightBraceToken.toString());
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        HashMap<String, RuntimeValue> dict= new HashMap<>();
        if(strLitList.size() == 0) {
            return new RuntimeDictValue(dict);
        }
        for(int i = 0; i < strLitList.size(); i++) {
            String str = strLitList.get(i).eval(curScope).getStringValue("string", this);
            RuntimeValue rV = this.exprsList.get(i).eval(curScope);
            dict.put(str, rV);
        }

        return new RuntimeDictValue(dict);
    }
}
