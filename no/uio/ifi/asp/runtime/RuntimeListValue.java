package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.*;

import java.util.ArrayList;

//Done and tested
public class RuntimeListValue extends RuntimeValue {
    private final boolean boolValue;
     ArrayList<RuntimeValue> listValue;

    public RuntimeListValue(ArrayList<RuntimeValue> list) {
        this.listValue = list;
        this.boolValue = !list.isEmpty();
    }

    @Override
    protected String typeName() {
        return "list";
    }
    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return this.boolValue;
    }

    //may be needed
    @Override
    public String showInfo() {
        return toString();
    }

    @Override
    public ArrayList<RuntimeValue> getListValue(String what, AspSyntax where) {
        return this.listValue;
    }

    @Override
    public RuntimeValue evalLen(AspSyntax where) {
        return new RuntimeIntValue(listValue.size());
    }

    @Override
    public String toString() {
        String strList = "";
        boolean inn = false;
        for (RuntimeValue aListValue : listValue) {
            if(inn) {
                strList += ", ";
            }
            strList += aListValue.toString();
            inn = true;
        }

        return "[" + strList + "]";
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(!boolValue);
    }

    //in case of Any == none
    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        RuntimeValue res = null;
        if (v instanceof RuntimeNoneValue) {
            res = new RuntimeBoolValue(false);
        } else {
            runtimeError("Error! at RuntimeListValue.evalEqual : " + v.getClass(), where);
        }
        return res;
    }

    //in case of Any == none just the -ve of equal
    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        RuntimeValue res = null;
        if (v instanceof RuntimeNoneValue) {
            res = new RuntimeBoolValue(true);
        } else {
            runtimeError("Error! at RuntimeListValue.evalNotEqual : " + v.getClass(), where);
        }
        return res;
    }

    /*
    * appends the List values on the current instance with the
    * RuntimeValue which comes as arg it it is list instance */
    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
        RuntimeValue res = null;
        if (v instanceof RuntimeListValue) {
            ArrayList<RuntimeValue> temp = new ArrayList<>();
            temp.addAll(listValue);
            temp.addAll(v.getListValue(" + ", where));
            res =  new RuntimeListValue(temp);
        } else {
            runtimeError("Error! at RuntimeListValue.evalAdd: " + v.getClass(), where);
        }
        return res;
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        RuntimeValue res = null;
        if (v instanceof RuntimeIntValue) {
            ArrayList<RuntimeValue> temp = new ArrayList<>();
            long fact = v.getIntValue("*", where);
            while (fact>0) {
                temp.addAll(listValue);
                fact--;
            }
            res =  new RuntimeListValue(temp);
        } else {
            runtimeError("Error! at RuntimeListValue.evalAdd: " + v.getClass(), where);
        }
        return res;
    }

    @Override
    public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {
        RuntimeValue res = null;
        if (v instanceof RuntimeIntValue) {
            res = listValue.get((int) v.getIntValue("subscription", where));
        } else {
            runtimeError("Error at RuntimeListValue.evalSubscription: " +
                    v.getClass(),where);
        }
        return res;
    }

}
