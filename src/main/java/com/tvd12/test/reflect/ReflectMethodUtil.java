package com.tvd12.test.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectMethodUtil {
    
    //prevent new instance
    private ReflectMethodUtil() {}
    
    /**
     * Get a method (public, private, protected, package access) 
     * with the given method name and the given parameter types, 
     * declared on the given class or one of its superclasses.
     * 
     * @param methodName method name
     * @param clazz class to find
     * @param parameterTypes array of parameter types
     * @return a method
     * @exception IllegalStateException when method not exists
     */
    public static Method getMethod(String methodName, 
            Class<?> clazz, Class<?>... parameterTypes) {
        Method method = null;
        Class<?> superClass = clazz;
        while(method == null && superClass != null) {
            try {
                method = superClass.getDeclaredMethod(methodName, parameterTypes);
            } catch (NoSuchMethodException | SecurityException e) {
                superClass = superClass.getSuperclass();
                if(superClass == Object.class) 
                    throw new IllegalStateException("Has no declared methods " + 
                            methodName + " on class " + clazz, e);
            }
        }
        method.setAccessible(true);
        return method;
    }
    
    /**
     * Get a method (public, private, protected, package access) 
     * with the given method name and the given parameter types, 
     * declared on the given class or one of its superclasses. 
     * 
     * Note: unsupport params is primitive types
     * 
     * @param methodName method name
     * @param object object to find
     * @param params array of parameters
     * @return a method
     * @exception IllegalStateException when method not exists
     */
    public static Method getMethod(String methodName, 
            Object object, Object... params) {
        Class<?>[] types = getParameterTypes(params);
        return getMethod(methodName, object.getClass(), types);
    }
    
    /**
     * Get parameter types from array of parameter values
     * 
     * @param params array of parameters 
     * @return array of parameter types
     */
    private static Class<?>[] getParameterTypes(Object... params) {
        Class<?> types[] = null;
        if(params != null) {
            types = new Class<?>[params.length];
            for(int i = 0 ; i < params.length ; i++) {
                types[i] = params[i].getClass();
            }
        }
        return types;
    }
    
    /**
     * Invokes a method whose parameter types match exactly the parameter types given.
     * 
     * @param method method to invoke
     * @param object invoke method on this object 
     * @param params array of parameter
     * @return the value returned by the invoked method
     * @exception IllegalStateException when can not invoke method
     */
    public static Object invokeMethod(Method method, 
            Object object, Object... params) {
        try {
            return method.invoke(object, params);
        } catch (IllegalAccessException 
                | IllegalArgumentException 
                | InvocationTargetException e) {
            throw new IllegalStateException("Can not invoke method " + method.getName()
                       + " on class " + ((object != null) ? object.getClass() : ""), e);
        }
    }
    
    /**
     * Invokes a method whose parameter types match exactly the parameter types given.
     * Note: unsupport params is primitive types
     * 
     * @param methodName method name
     * @param object invoke method on this object 
     * @param params array of parameter
     * @return the value returned by the invoked method
     */
    public static Object invokeMethod(String methodName, 
            Object object, Object... params) {
        Method method = getMethod(methodName, object, params);
        return invokeMethod(method, object, params);
    }
    
    /**
     * Invokes a static method whose parameter types match exactly the parameter types given.
     * Note: unsupport params is primitive types
     * 
     * @param methodName method name
     * @param clazz invoke method on this class 
     * @param params array of parameter
     * @return the value returned by the invoked method
     */
    public static Object invokeStaticMethod(String methodName, 
            Class<?> clazz, Object... params) {
        Class<?>[] types = getParameterTypes(params);
        Method method = getMethod(methodName, clazz, types);
        return invokeMethod(method, null, params);
    }
    
    /**
     * Invokes a static method whose parameter types match exactly the parameter types given.
     * 
     * @param method method to invoke
     * @param params array of parameter
     * @return the value returned by the invoked method
     */
    public static Object invokeStaticMethod(Method method, Object... params) {
        return invokeMethod(method, null, params);
    }
    
}
