package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.parser.AspSyntax;

import java.util.HashMap;

/**
* Done and Tested
*/
public class RuntimeDictValue extends RuntimeValue{
    private HashMap<String, RuntimeValue> dictValue;
    private final boolean boolValue;




    public RuntimeDictValue(HashMap<String, RuntimeValue> dictValueIn) {
        dictValue = dictValueIn;
        boolValue = (!dictValueIn.isEmpty())? true:false;

    }

    @Override
    protected String typeName() {
        return "dict";
    }

    @Override
    public String showInfo() {
        return toString();
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return this.boolValue;
    }

    @Override
    public RuntimeValue evalLen(AspSyntax where) {
        return new RuntimeIntValue(this.dictValue.size());
    }

    public RuntimeValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(!this.boolValue);
    }

    // ok
    @Override
    public String toString() {
        String strOut = "";
        boolean comma = false;
        for(HashMap.Entry<String, RuntimeValue> entry : this.dictValue.entrySet()) {
            if (comma){
                strOut += ", ";
            }
            strOut += "'"+entry.getKey().toString()+"'" + ":" + entry.getValue().toString();
            comma = true;
        }
        return "{"+ strOut  + "}";
    }

    /**
    * any == none implementations
    * assume any == none is false
    * */
    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        RuntimeValue res = null;
        if (v instanceof RuntimeNoneValue) {
            res = new RuntimeBoolValue(false);
        } else {
            runtimeError("Error! at RuntimeDictValue.evalEqual : " +
                    v.getClass(), where);
        }
    return res;
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        RuntimeValue res = null;
        if (v instanceof RuntimeNoneValue) {
            res = new RuntimeBoolValue(true);
        } else {
            runtimeError("Error! at RuntimeDictValue.evalEqual : " +
                    v.getClass(), where);
        }
        return res;
    }

    // bit un certain
    public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {
        RuntimeValue res = null;

        if (v instanceof RuntimeStringValue) {
            res = dictValue.get(v.getStringValue("subscription", where));
             //runtimeError("noe feil her med at index ikke finnes...: " + v.getClass(), where);

        } else {
            runtimeError("Error! at RuntimeDictValue.evalSubscription " +
                    v.getClass(), where);

        }
        return res;
    }


}
