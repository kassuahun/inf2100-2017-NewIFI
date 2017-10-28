package no.uio.ifi.asp.runtime;

public class RuntimeStringValue extends RuntimeValue {
    String strValue;

    @Override
    protected String typeName() {
        return null;
    }

    @Override
    public String showInfo() {
        if (strValue.indexOf('\'') >= 0)
            return "\"" + strValue + "\"";
        else
            return "’" + strValue + "'";
    }
}
