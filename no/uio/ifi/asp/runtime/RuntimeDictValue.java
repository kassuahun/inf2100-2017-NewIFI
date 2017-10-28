package no.uio.ifi.asp.runtime;

import java.util.HashMap;

public class RuntimeDictValue extends RuntimeValue{
    private HashMap<String, RuntimeValue> dictValue;

    
    
    
    public RuntimeDictValue(HashMap<String, RuntimeValue> dict) {
        super();
    }


    @Override
    protected String typeName() {
        return "dict";
    }
}
