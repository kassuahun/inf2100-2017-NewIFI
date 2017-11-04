package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspFuncDef;
import no.uio.ifi.asp.parser.AspSyntax;

import java.util.ArrayList;

public class RuntimeFunc extends RuntimeValue {

    AspFuncDef funcDef = null;
    String functionName;
    public RuntimeFunc(String functionName, AspFuncDef funcDef){
        this.funcDef = funcDef;
        this.functionName = functionName;
    }

    @Override
    protected String typeName() {
        return null;
    }



    @Override
    public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams,
                                     RuntimeScope scope,
                                     AspSyntax where) {


        return null;
    }

}
