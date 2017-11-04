package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

//Done Tested
public class RuntimeStringValue extends RuntimeValue {
    String strValue;
    boolean emptyStr;

    public RuntimeStringValue(String s) {
        this.strValue = s;
        this.emptyStr = (!s.equals("")); // hvis s er tom, er bool false.
    }



    @Override
    public String toString() {
        return strValue;
    }

    @Override
    protected String typeName() {
        return "String";
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return emptyStr;
    }

    @Override
    public String getStringValue(String what, AspSyntax where) {
        return this.toString();
    }
/*
    @Override
    public String showInfo() {
        if (strValue.indexOf('\'') >= 0)
            return "\"" + strValue + "\"";
        else
            return "'" + strValue + "'";
    }
*/
    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        RuntimeValue res = new RuntimeBoolValue(this.emptyStr);
        return res;
    }

    @Override
    public RuntimeValue evalLen(AspSyntax where) {
        RuntimeValue res = new RuntimeIntValue(this.strValue.length());
        return res;
    }

    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
        RuntimeValue res = null;
        if (v instanceof RuntimeStringValue) {
            String v2 = v.getStringValue(" + ", where);
            res =  new RuntimeStringValue(strValue + v2);
        } else {
            runtimeError("Error! RuntimeStringValue.evalAdd : " + v.getClass(), where);
        }
        return res;
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        RuntimeValue res = null;
        if (v instanceof RuntimeIntValue) {
            long v2 = v.getIntValue(" * ", where);
            String strRes = "";
            for (int i = 0; i < v2; i++) {
                strRes += strValue;
            }
            res =  new RuntimeStringValue(strRes);
        } else {
            runtimeError("Error! RuntimeStringValue.evalMultiply : " + v.getClass(), where);
        }
        return res;
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        RuntimeValue res = null;
        if (v instanceof RuntimeStringValue) {
            String v2 = v.getStringValue(" == ", where);
            res = new RuntimeBoolValue(strValue.equals(v2));
        } else if (v instanceof RuntimeNoneValue) {
            res = new RuntimeBoolValue(false);
        } else {
            runtimeError("Error! RuntimeStringValue.evalEqual : " +
                    v.getClass(), where);
        }
        return res;
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        RuntimeValue res = null;
        if (v instanceof RuntimeStringValue) {
            String v2 = v.getStringValue(" != ", where);
            res = new RuntimeBoolValue(!strValue.equals(v2));
        } else if (v instanceof RuntimeNoneValue) {
            res = new RuntimeBoolValue(false);
        } else {
            runtimeError("Error! RuntimeStringValue.evalEqual : " +
                    v.getClass(), where);
        }
        return res;
    }

    @Override
    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
        RuntimeValue res = null;
        if (v instanceof RuntimeStringValue) {
            String v2 = v.getStringValue(" < ", where);
            if(strValue.compareTo(v2) < 0) {
                res = new RuntimeBoolValue(true);
            } else {
                res = new RuntimeBoolValue(false);
            }
        } else {
            runtimeError("Error! RuntimeStringValue.evalLess : " +
                    v.getClass(), where);
            }
        return res;
    }

    @Override
    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
        RuntimeValue res = null;
        if (v instanceof RuntimeStringValue) {
            String v2 = v.getStringValue(" <= ", where);
            if(strValue.compareTo(v2) <= 0) {
                res = new RuntimeBoolValue(true);
            } else {
                res = new RuntimeBoolValue(false);
            }
        } else {
            runtimeError("Error! RuntimeStringValue.evalLessEqual : " +
                    v.getClass(), where);
        }
        return res;
    }

    @Override
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
        RuntimeValue res = null;
        if (v instanceof RuntimeStringValue) {
            String v2 = v.getStringValue(" > ", where);
            if(strValue.compareTo(v2) > 0) {
                res = new RuntimeBoolValue(true);
            } else {
                res = new RuntimeBoolValue(false);
            }
        } else {
            runtimeError("Error! RuntimeStringValue.evalGreater : " +
                    v.getClass(), where);
        }
        return res;
    }

    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
        RuntimeValue res = null;
        if (v instanceof RuntimeStringValue) {
            String v2 = v.getStringValue(" >= ", where);
            if(strValue.compareTo(v2) >= 0) {
                res = new RuntimeBoolValue(true);
            } else {
                res = new RuntimeBoolValue(false);
            }
        } else {
            runtimeError("Error! RuntimeStringValue.evalGreaterEqual : " +
                    v.getClass(), where);
        }
        return res;
    }

}
