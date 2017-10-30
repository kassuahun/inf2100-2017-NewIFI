package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

import static no.uio.ifi.asp.scanner.TokenKind.nameToken;

public class AspName extends AspAtom{
    String tokenName;

    AspName(int n) {
        super(n);
    }

    public static AspName parse(Scanner s) {
        Main.log.enterParser("name");
        AspName name = new AspName(s.curLineNum());
        if (s.curToken().kind == nameToken){
          name.tokenName = s.curToken().name;

        }
        skip(s,nameToken);

        Main.log.leaveParser("name");
        return name;
    }

    public String getTokenName() {
        return tokenName;
    }

    @Override
    protected void prettyPrint() {
        Main.log.prettyWrite(tokenName.toString());

    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {

        return curScope.find(tokenName, this);
    }
}
