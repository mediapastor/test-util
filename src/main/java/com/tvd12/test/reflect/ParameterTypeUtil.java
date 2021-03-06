package com.tvd12.test.reflect;

import java.util.List;

public class ParameterTypeUtil {

    // prevent new instance
    private ParameterTypeUtil() {}
    
    /**
     * Build array of parameters types to string
     * 
     * @param parameterTypes array of parameters
     * @return a string
     */
    public static String toString(Class<?>... parameterTypes) {
        if(parameterTypes == null)
            return "";
        StringBuilder builder = new StringBuilder();
        for(int i = 0 ; i < parameterTypes.length ; i++) 
            builder.append(parameterTypes[i])
                .append((i < parameterTypes.length - 1) ? ", " : "");
        return builder.toString();
    }
    
    /**
     * Build list of parameters types to string
     * 
     * @param parameterTypes list of parameter types
     * @return
     */
    public static String toString(List<Class<?>> parameterTypes) {
        if(parameterTypes == null)
            return "";
        return toString(parameterTypes.toArray(new Class<?>[parameterTypes.size()]));
    }
}
