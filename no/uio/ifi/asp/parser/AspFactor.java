package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

import java.util.ArrayList;

public class AspFactor extends AspSyntax{
    AspFactorPrefix fPrefix = null; // optional
    ArrayList<AspPrimary> primaryList = new ArrayList<AspPrimary>();
    ArrayList<AspFactorOpr> facOprList = new ArrayList<AspFactorOpr>();


    AspFactor(int n) {
        super(n);
    }

    public static AspFactor parse(Scanner s) {
        Main.log.enterParser("factor");
        AspFactor factor = new AspFactor(s.curLineNum());

        if(s.isFactorPrefix()) {
            factor.fPrefix = AspFactorPrefix.parse(s);
        }
        while(true) {
            factor.primaryList.add(AspPrimary.parse(s));
            if(! s.isFactorOpr()) break;
            factor.facOprList.add(AspFactorOpr.parse(s));
        }
        Main.log.leaveParser("factor");
        return factor;
    }

    @Override
    protected void prettyPrint() {
        if (fPrefix!=null) {
            fPrefix.prettyPrint();
        }
        primaryList.get(0).prettyPrint();
        for (int i = 0; i < facOprList.size(); i++) {
            facOprList.get(i).prettyPrint();
            primaryList.get(i+1).prettyPrint();
        }

    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {

        RuntimeValue v = primaryList.get(0).eval(curScope);
        if(fPrefix != null) {

            switch (fPrefix.oprType) {
                case minusToken:
                    v = v.evalNegate(this);
                    break;
                case plusToken:
                    v= v.evalPositive(this);
                    break;
                default:
                    Main.panic("Illegal term operator: " + fPrefix.oprType + "!");
            }
        }

        for (int i = 1;  i < primaryList .size();  ++i) {
            TokenKind factOprType = facOprList.get(i-1).factOprType;
            switch (factOprType) {
                case astToken:
                    v = v.evalMultiply(primaryList.get(i).eval(curScope), this);
                    break;
                case slashToken:
                    v = v.evalDivide(primaryList.get(i).eval(curScope), this);
                    break;
                case percentToken:
                    v = v.evalModulo(primaryList.get(i).eval(curScope), this);
                    break;
                case doubleSlashToken:
                    v = v.evalIntDivide(primaryList.get(i).eval(curScope), this);
                    break;
                default:
                    Main.panic("Illegal term operator: " + factOprType + "!");
            }
        }
        return v;
    }
}



