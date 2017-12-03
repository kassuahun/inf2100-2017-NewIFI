package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;

import java.util.ArrayList;

public class AspPrimary extends AspSyntax{

    ArrayList<AspPrimarySuffix> prSuffix = new ArrayList<AspPrimarySuffix>();
    AspAtom atom = null;

    AspPrimary(int n) {
        super(n);
    }

    public static AspPrimary parse(Scanner s) {
        Main.log.enterParser("primary");
        AspPrimary primary = new AspPrimary(s.curLineNum());

        primary.atom = AspAtom.parse(s);
        if(s.isPrimaryPrefix()){
            while(true){
                primary.prSuffix.add(AspPrimarySuffix.parse(s));
                if (!s.isPrimaryPrefix()) {
                    break;
                }
            }
        }

        Main.log.leaveParser("primary");
        return primary;
    }

    @Override
    protected void prettyPrint() {
        atom.prettyPrint();
        for (AspPrimarySuffix x: prSuffix) {
            x.prettyPrint();
        }

    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue res = null;

        if (prSuffix.size() == 0){
           res = atom.eval(curScope);

        } else if (atom instanceof AspStringLiteral){

            int index = (int) prSuffix.get(0).eval(curScope).getIntValue("AspPrimary.eval", this);
            res = new RuntimeStringValue(""+atom.eval(curScope).getStringValue("AspPrimary.eval", this).charAt(index));

        } else{
            res = atom.eval(curScope);
            for (AspPrimarySuffix prx : prSuffix) {
                if (prx instanceof AspSubscription) {
                    res = res.evalSubscription(prx.eval(curScope), this);
                } else if (prx instanceof AspArguments) {
                    //res = res.(prx.eval(curScope),this); // am not sure How this Works
                }
            }
        }

        return res;
    }


}
