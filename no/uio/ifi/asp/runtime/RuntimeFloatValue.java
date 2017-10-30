package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeFloatValue extends RuntimeValue{
    private double floatValue;
    private boolean boolValue;


    public RuntimeFloatValue(double d) {
        this.floatValue = d;
        this.boolValue = (d != 0.0);

    }

    public String toString() {
        return "" + floatValue;
    }

    @Override
    protected String typeName() {
        return "float";
    }

    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {

        return null;
    }


    }
