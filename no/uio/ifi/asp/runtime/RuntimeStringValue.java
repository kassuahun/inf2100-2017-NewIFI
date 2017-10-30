package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeStringValue extends RuntimeValue {
    String strValue;
    boolean emptyStr

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
    @Override
    public String showInfo() {
        if (strValue.indexOf('\'') >= 0)
            return "\"" + strValue + "\"";
        else
            return "’" + strValue + "'";
    }
}
