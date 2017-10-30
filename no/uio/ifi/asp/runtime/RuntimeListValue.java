package no.uio.ifi.asp.runtime;

import java.util.ArrayList;

public class RuntimeListValue extends RuntimeValue {

    public RuntimeListValue(ArrayList<RuntimeValue> actualParams) {
        super();
    }

    @Override
    protected String typeName() {
        return null;
    }
}
