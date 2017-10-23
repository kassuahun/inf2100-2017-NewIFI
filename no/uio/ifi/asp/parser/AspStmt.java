package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public abstract class AspStmt extends AspSyntax {
    static AspStmt st = null;
    public AspStmt(int i) {
        super(i);
    }

    public static AspStmt parse(Scanner s) {
        Main.log.enterParser("stmt");
        //AspStmt st = null;

            switch (s.curToken().kind) {
                case nameToken:
                    if (s.anyEqualToken()){
                        st = AspAssignment.parse(s);
                    }
                    else{
                        st = AspExprStmt.parse(s);
                    }
                    break;
                    /* exeptional
                case integerToken:
                    st= AspExprStmt.parse(s);
                    break;
                case floatToken :
                    st= AspExprStmt.parse(s);
                    break;
                case stringToken:
                    st= AspExprStmt.parse(s);
                    break;
                    *///end of exeptionals
                case ifToken:
                    st = AspIfStmt.parse(s);
                    break;
                case whileToken:
                    st = AspWhileStmt.parse(s);
                    break;
                case returnToken:
                    st = AspReturnStmt.parse(s);
                    break;
                case passToken:
                    st = AspPassStmt.parse(s);
                    break;
                case defToken:
                    st = AspFuncDef.parse(s);
                    break;
                default:
                   // st= AspExprStmt.parse(s);
                    parserError("UnExpected Asp statement  " +
                            s.curToken().kind + "!", s.curLineNum());
        }

        Main.log.leaveParser("stmt");
        return st;
    }


    @Override
    public  void prettyPrint() {
        if(st instanceof AspAssignment){
            ((AspAssignment)st).prettyPrint();
        }else if(st instanceof AspExprStmt){
            ((AspExprStmt)st).prettyPrint();
        }else if(st instanceof AspIfStmt){
            ((AspIfStmt)st).prettyPrint();
        }else if(st instanceof AspWhileStmt){
            ((AspWhileStmt)st).prettyPrint();
        }else if(st instanceof AspReturnStmt){
            ((AspReturnStmt)st).prettyPrint();
        }else if(st instanceof AspPassStmt){
            ((AspPassStmt)st).prettyPrint();
        }else if(st instanceof AspFuncDef){
            ((AspFuncDef)st).prettyPrint();
        }

    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue, RuntimeReturnValue {
        return null;
    }
}
