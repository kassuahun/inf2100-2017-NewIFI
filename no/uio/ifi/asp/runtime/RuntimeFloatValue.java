package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

/**
 * Done and Tested
 */
public class RuntimeFloatValue extends RuntimeValue{
    private double floatValue;
    private boolean boolValue;

    public RuntimeFloatValue(double d) {
        this.floatValue = d;
        this.boolValue = (d != 0.0)? true:false;
    }

    @Override
    public String toString() {
        return "" + floatValue;
    }

    @Override
    protected String typeName() {
        return "float";
    }

    @Override
    public double getFloatValue(String what, AspSyntax where) {
        return floatValue;
    }

    @Override
    public long getIntValue(String what, AspSyntax where) {
        return (int)floatValue;
    }

    @Override
    public RuntimeValue evalNegate(AspSyntax where) {
        RuntimeValue res = new RuntimeFloatValue(-floatValue);
        return res;
    }

    @Override
    public RuntimeValue evalPositive(AspSyntax where) {
        RuntimeValue res = new RuntimeFloatValue(floatValue);
        return res;
    }

    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
            RuntimeValue res = null;
        if(v instanceof RuntimeFloatValue) {
            double d = v.getFloatValue("+ Operator", where);
            res = new RuntimeFloatValue(floatValue + d);
        }else if(v instanceof RuntimeIntValue) {
            long l = v.getIntValue("+ Operator", where);
            res = new RuntimeFloatValue(floatValue + l);
        }else {
            runtimeError("Type error for +.", where);
        }
        return res;
    }

    @Override
    public RuntimeValue evalSubtract(RuntimeValue v, AspSyntax where) {
        RuntimeValue res = null;
        if(v instanceof RuntimeFloatValue) {
            double d = v.getFloatValue("- Operator", where);
            res = new RuntimeFloatValue(floatValue - d);
        }else if(v instanceof RuntimeIntValue) {
            long l = v.getIntValue("- Operator", where);
            res = new RuntimeFloatValue(floatValue - l);
        }else {
            runtimeError("Type error for -.", where);
        }
        return res;
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        RuntimeValue res = null;
        if(v instanceof RuntimeFloatValue) {
            double d = v.getFloatValue("* Operator", where);
            res = new RuntimeFloatValue(floatValue * d);
        }else if(v instanceof RuntimeIntValue) {
            long l = v.getIntValue("* Operator", where);
            res = new RuntimeFloatValue(floatValue * l);
        }else {
            runtimeError("Type error for *.", where);
        }
        return res;
    }

    @Override
    public RuntimeValue evalDivide(RuntimeValue v, AspSyntax where) {
        RuntimeValue res = null;
        if(v instanceof RuntimeFloatValue) {
            double d = v.getFloatValue("/ Operator", where);
            res = new RuntimeFloatValue(floatValue / d);
        }else if(v instanceof RuntimeIntValue) {
            long l = v.getIntValue("/ Operator", where);
            res = new RuntimeFloatValue(floatValue / l);
        }else {
            runtimeError("Type error for /.", where);
        }
        return res;
    }

    @Override
    public RuntimeValue evalIntDivide(RuntimeValue v, AspSyntax where) {
        RuntimeValue res = null;
        if(v instanceof RuntimeFloatValue) {
            double d = v.getFloatValue("// Operator", where);
            res = new RuntimeFloatValue(Math.floor(floatValue / d));
        }else if(v instanceof RuntimeIntValue) {
            long l = v.getIntValue("// Operator", where);
            res = new RuntimeFloatValue(Math.floor(floatValue / l));
        }else {
            runtimeError("Type error for //.", where);
        }
        return res;
    }

    @Override
    public RuntimeValue evalModulo(RuntimeValue v, AspSyntax where) {
        RuntimeValue res = null;
        if(v instanceof RuntimeFloatValue) {
            double d = v.getFloatValue("% Operator", where);
            res = new RuntimeFloatValue(floatValue - d * Math.floor(floatValue/d));
        }else if(v instanceof RuntimeIntValue) {
            long l = v.getIntValue("% Operator", where);
            res = new RuntimeFloatValue(floatValue - l * Math.floor(floatValue/l));
        }else {
            runtimeError("Type error for %.", where);
        }
        return res;
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        RuntimeValue res = null;
        if (v instanceof RuntimeFloatValue ) {
            double d = v.getFloatValue(" != Operator", where);
            res = new RuntimeBoolValue(d != floatValue);
        } else if(v instanceof RuntimeIntValue) {
            long l = v.getIntValue(" !=  Operator", where);
            res = new RuntimeBoolValue(l!=floatValue);
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
        if (v instanceof RuntimeFloatValue ) {
            double d = v.getFloatValue(" == Operator", where);
            res = new RuntimeBoolValue(d == floatValue);
        } else if(v instanceof RuntimeIntValue) {
            long l = v.getIntValue(" ==  Operator", where);
            res = new RuntimeBoolValue(l==floatValue);
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
        if (v instanceof RuntimeFloatValue ) {
            double d = v.getFloatValue(" < Operator", where);
            res = new RuntimeBoolValue(floatValue < d);
        } else if(v instanceof RuntimeIntValue) {
            long l = v.getIntValue(" < Operator", where);
            res = new RuntimeBoolValue(floatValue < l);
        }else {
            runtimeError("Type error for <.", where);
        }
        return res;
    }

    @Override
    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
        RuntimeValue res = null;
        if (v instanceof RuntimeFloatValue) {
            double d = v.getFloatValue(" <= Operator", where);
            res = new RuntimeBoolValue(floatValue <= d);
        } else if(v instanceof RuntimeIntValue) {
            long l = v.getIntValue(" <= Operator", where);
            res = new RuntimeBoolValue(floatValue <= l);
        }else {
            runtimeError("Type error for <=.", where);
        }
        return res;
    }

    @Override
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
        RuntimeValue res = null;
        if (v instanceof RuntimeFloatValue ) {
            double d = v.getFloatValue(" > Operator", where);
            res = new RuntimeBoolValue(floatValue > d);
        } else if(v instanceof RuntimeIntValue) {
            long l = v.getIntValue(" > Operator", where);
            res = new RuntimeBoolValue(floatValue > l);
        }else {
            runtimeError("Type error for >.", where);
        }
        return res;
    }

    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
        RuntimeValue res = null;
        if (v instanceof RuntimeFloatValue ) {
            double d = v.getFloatValue(" >= Operator", where);
            res = new RuntimeBoolValue(floatValue >= d);
        } else if(v instanceof RuntimeIntValue) {
            long l = v.getIntValue(" >= Operator", where);
            res = new RuntimeBoolValue(floatValue >= l);
        }else {
            runtimeError("Type error for >=.", where);
        }
        return res;
    }

}
