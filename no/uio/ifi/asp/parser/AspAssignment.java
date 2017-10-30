package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

import java.util.ArrayList;

import static no.uio.ifi.asp.scanner.TokenKind.*;


/**
 *
 */
public class AspAssignment extends AspStmt{
    ArrayList<AspSubscription> sub = new ArrayList<>();
    AspName name;
    AspExpr expr;
    //newline token
    public AspAssignment(int i) {
        super(i);
    }

    public static AspAssignment parse(Scanner s) {
        Main.log.enterParser("assignment");
        AspAssignment ass = new AspAssignment(s.curLineNum());

        //test(s, nameToken);
        ass.name = AspName.parse(s);
        //skip(s,nameToken);
        //if [
        if(s.curToken().kind == leftBracketToken) {
            while(true){
                ass.sub.add(AspSubscription.parse(s));
                //if [][
                if(s.curToken().kind != leftBracketToken){
                    break;
                }
            }
        }
        skip(s,equalToken);
        ass.expr= AspExpr.parse(s);
        skip(s,newLineToken);

        Main.log.leaveParser("assignment");
        return ass;
    }

    @Override
    public void prettyPrint() {
        name.prettyPrint();
        if (sub.size()>0){
            for (AspSubscription s: sub) {
                s.prettyPrint();
            }
        }
        Main.log.prettyWrite(" "+equalToken.toString() + " ");
        expr.prettyPrint();
        Main.log.prettyWriteLn();
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue rtvExpr = expr.eval(curScope);
        RuntimeValue rtvName = null;
        if (sub.size() >0){
           rtvName = name.eval(curScope);
            for (int i = 0; i < sub.size()-1 ; i++) {
                rtvName = rtvName.evalSubscription(sub.get(i).eval(curScope), this);
            }

            rtvName.evalAssignElem(sub.get(sub.size()-1).eval(curScope), rtvExpr, this);

        }else{
            // not sure
            curScope.assign(name.getTokenName(), rtvExpr);
        }

        //return rtvName; dont understand
        return null;
    }
}
