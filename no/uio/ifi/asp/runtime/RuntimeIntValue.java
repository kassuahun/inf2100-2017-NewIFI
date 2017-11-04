package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

/**
 * Tested and work
 */
public class RuntimeIntValue extends RuntimeValue {
    long intValue;
    boolean boolValue;

    public RuntimeIntValue(long v) {
        intValue = v;
        boolValue = (v !=0)? true:false;
    }

    @Override
    protected String typeName() {
        return "int";
    }

    @Override
    public String toString() {
        return "" + intValue;
    }

    @Override
    public double getFloatValue(String what, AspSyntax where) {
        return (double)intValue;
    }

    @Override
    public long getIntValue(String what, AspSyntax where) {
        return intValue;
    }

    @Override
    public RuntimeValue evalNegate(AspSyntax where) {
        RuntimeValue res = new RuntimeIntValue(-intValue);
        return res;
    }

    @Override
    public RuntimeValue evalPositive(AspSyntax where) {
        RuntimeValue res = new RuntimeIntValue(intValue);
        return res;
    }

    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
        RuntimeValue res = null;
        if (v instanceof RuntimeIntValue) {
            long v2 = v.getIntValue("+ Operator", where);
            res = new RuntimeIntValue(intValue + v2);
        } else if (v instanceof RuntimeFloatValue) {
            double v2 = v.getFloatValue("+ Operator", where);
            res = new RuntimeFloatValue(intValue + v2);
        } else {
            runtimeError("Type error for +.", where);
        }
        return res;
    }

    @Override
    public RuntimeValue evalSubtract(RuntimeValue v, AspSyntax where) {
        RuntimeValue res = null;
        if (v instanceof RuntimeIntValue) {
            long v2 = v.getIntValue("- Operator", where);
            res = new RuntimeIntValue(intValue - v2);
        } else if (v instanceof RuntimeFloatValue) {
            double v2 = v.getFloatValue("- Operator", where);
            res = new RuntimeFloatValue(intValue - v2);
        } else {
            runtimeError("Type error for -.", where);
        }
        return res;
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        RuntimeValue res = null;
        if (v instanceof RuntimeIntValue) {
            long v2 = v.getIntValue("* Operator", where);
            res = new RuntimeIntValue(intValue * v2);
        } else if (v instanceof RuntimeFloatValue) {
            double v2 = v.getFloatValue("* Operator", where);
            res = new RuntimeFloatValue(intValue * v2);
        } else {
            runtimeError("Type error for *.", where);
        }
        return res;
    }

    @Override
    public RuntimeValue evalDivide(RuntimeValue v, AspSyntax where) {
        RuntimeValue res = null;
        if (v instanceof RuntimeIntValue) {
            long v2 = v.getIntValue("/ Operator", where);
            res = new RuntimeIntValue(intValue / v2);
        } else if (v instanceof RuntimeFloatValue) {
            double v2 = v.getFloatValue("/ Operator", where);
            res = new RuntimeFloatValue(intValue / v2);
        } else {
            runtimeError("Type error for /.", where);
        }
        return res;
    }

    @Override
    public RuntimeValue evalIntDivide(RuntimeValue v, AspSyntax where) {
        RuntimeValue res = null;
        if (v instanceof RuntimeIntValue) {
            long v2 = v.getIntValue("// Operator", where);
            res = new RuntimeIntValue(Math.floorDiv(intValue, v2));
        } else if (v instanceof RuntimeFloatValue) {
            double v2 = v.getFloatValue("/ Operator", where);
            res = new RuntimeFloatValue(Math.floor(intValue / v2));
        } else {
            runtimeError("Type error for //.", where);
        }
        return res;
    }

    @Override
    public RuntimeValue evalModulo(RuntimeValue v, AspSyntax where) {
        RuntimeValue res = null;
        if(v instanceof RuntimeIntValue) {
            long d = v.getIntValue("% Operator", where);
            res = new RuntimeIntValue(Math.floorMod(intValue,d));
        }else if(v instanceof RuntimeFloatValue) {
            double l = v.getFloatValue("% Operator", where);
            res = new RuntimeFloatValue(intValue - l * Math.floor(intValue/l));
        }else {
            runtimeError("Type error for %.", where);
        }
        return res;
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        RuntimeValue res = null;
        if (v instanceof RuntimeIntValue ) {
            long l = v.getIntValue(" != Operator", where);
            res = new RuntimeBoolValue(l != intValue);
        } else if(v instanceof RuntimeFloatValue) {
            double d = v.getFloatValue(" !=  Operator", where);
            res = new RuntimeBoolValue(d != intValue);
        }else if(v instanceof RuntimeNoneValue) {
            res = new RuntimeBoolValue(false);
        }else {
            runtimeError("Type error for !=.", where);
        }
        return res;
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        RuntimeValue res = null;
        if (v instanceof RuntimeIntValue ) {
            long l = v.getIntValue(" == Operator", where);
            res = new RuntimeBoolValue(l == intValue);
        } else if(v instanceof RuntimeFloatValue) {
            double d = v.getFloatValue(" ==  Operator", where);
            res = new RuntimeBoolValue(d == intValue);
        }else if(v instanceof RuntimeNoneValue) {
            res = new RuntimeBoolValue(false);
        }else {
            runtimeError("Type error for ==.", where);
        }
        return res;
    }

    @Override
    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
        RuntimeValue res = null;
        if (v instanceof RuntimeIntValue ) {
            long l = v.getIntValue(" < Operator", where);
            res = new RuntimeBoolValue(intValue < l  );
        } else if(v instanceof RuntimeFloatValue) {
            double d = v.getFloatValue(" < Operator", where);
            res = new RuntimeBoolValue( intValue < d );
        }else {
            runtimeError("Type error for <.", where);
        }
        return res;
    }

    @Override
    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
        RuntimeValue res = null;
        if (v instanceof RuntimeIntValue ) {
            long l = v.getIntValue(" <= Operator", where);
            res = new RuntimeBoolValue(intValue <= l  );
        } else if(v instanceof RuntimeFloatValue) {
            double d = v.getFloatValue(" <= Operator", where);
            res = new RuntimeBoolValue( intValue < d);
        }else {
            runtimeError("Type error for <=.", where);
        }
        return res;
    }

    @Override
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
        RuntimeValue res = null;
        if (v instanceof RuntimeIntValue ) {
            long l = v.getIntValue(" > Operator", where);
            res = new RuntimeBoolValue(intValue > l  );
        } else if(v instanceof RuntimeFloatValue) {
            double d = v.getFloatValue(" > Operator", where);
            res = new RuntimeBoolValue( intValue > d );
        }else {
            runtimeError("Type error for >.", where);
        }
        return res;
    }

    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
        RuntimeValue res = null;
        if (v instanceof RuntimeIntValue ) {
            long l = v.getIntValue(" >= Operator", where);
            res = new RuntimeBoolValue(intValue >= l  );
        } else if(v instanceof RuntimeFloatValue) {
            double d = v.getFloatValue(" >= Operator", where);
            res = new RuntimeBoolValue( intValue > d);
        }else {
            runtimeError("Type error for >=.", where);
        }
        return res;
    }

}
