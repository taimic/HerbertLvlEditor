package utils.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import utils.string.StringUtils;

public class ClassModifier {
	public static final List<String> PACKAGES = new ArrayList<String>(){
		{
			add("scenes.");
			add("entity.");
			add("components.");
			add("components.drawables.");
			add("camera.");
		}
	};
	
	public static final List<String> METHOD_PREFIXES = new ArrayList<String>(){
		{
			add("");
			add("set");
			add("add");
		}
	};
	
	/**
	 * The class of the object to create.
	 */
	private static Class<?> objectClass;
	/**
	 * The current object that was created.
	 */
	private static Object currentObject;
	/**
	 * The methods of the object's class.
	 */
	private static Method[] methods;
	/**
	 * The constructor parameters that shall be used for initializing the object.
	 */
	private static ArrayList<Object> constructorParameters;
	/**
	 * The name of the current method that shall be called.
	 */
	private static String currentMethodName;
	
	// Creates an empty component of the given type
	public static <T> T createDefault(Class<T> type){
		objectClass = type;
		if(instantiateComponent()) return type.cast(currentObject);
		return null;
	}
	
	public static void setCurrentObject(Object object){
		currentObject = object;
	}
	
	/**
	 * Tries to instantiate the object with the current class.
	 * @return TRUE, if successful, FALSE otherwise.
	 */
	private static boolean instantiateComponent(){
		try {
			currentObject = objectClass.getConstructor().newInstance();
			return true;
		} catch (InstantiationException ie) {
			System.err.println("Could not instantiate component with the class: " + objectClass);
			ie.printStackTrace();
		} catch (IllegalAccessException iae) {
			iae.printStackTrace();
		} catch (IllegalArgumentException e) {
			System.err.println("Invalid constructor arguments for class: " + objectClass);
			e.printStackTrace();
		} catch (InvocationTargetException ite) {
			ite.printStackTrace();
		} catch (NoSuchMethodException nsme) {
			System.out.println("Constructor not found for class: " + objectClass);
			nsme.printStackTrace();
		} catch (SecurityException se) {
			System.err.println("Constructor not accessible for class: " + objectClass);
			se.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Retrieves all methods of the current object's class.
	 */
	public static void retrieveMethods(){
		retrieveMethods(objectClass);
	}
	
	public static void retrieveMethods(Class<?> objectClass){
		methods = objectClass.getMethods();
	}
	
	/**
	 * Calls the given method on the current object.
	 * @param method The method to call
	 * @return TRUE, if successful, FALSE otherwise.
	 */
	private static boolean callMethod(Method method){
		try {
			method.invoke(currentObject, constructorParameters.toArray());
			return true;
		} catch (IllegalAccessException iae) {
			iae.printStackTrace();
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage() + " for '" + currentMethodName + "(" + method.getParameterCount() + ")': " + constructorParameters.size());
			e.printStackTrace();
		} catch (InvocationTargetException ite) {
			ite.printStackTrace();
		}
		return false;
	}
	
	public static void updateComponentValues(String[] currentParameterValues, String methodName){
		// Loop through all methods of the class
        for(Method method : methods){
        	// The current method name, for easy comparison
        	currentMethodName = method.getName();
        	
        	// Check if the current method is the method we want to call
        	for(String methodPrefix : METHOD_PREFIXES){
        		if(currentMethodName.compareToIgnoreCase(methodPrefix + methodName) == 0){
        			// Check if we have the same parameter count, so we are sure to call the correct method
            		if(method.getParameterCount() == currentParameterValues.length){
            			// Initialize parameter list
            			constructorParameters = new ArrayList<Object>();
            			
            			Class<?>[] parameterTypes = method.getParameterTypes();
            			if(parameterTypes.length < 1) continue; 
            			
            			// Add the parameters, so we can call the method later on
    				    for(int i = 0; i < currentParameterValues.length; i++){
    				    	constructorParameters.add(parseValue(currentParameterValues[i], parameterTypes[i]));
    				    }

    			        // Call the current method
    			        callMethod(method);
            		}			        		
        		}
        	}
        }
	}
	
	public static void updateComponentValues(Object object, String methodName){
		// Loop through all methods of the class
        for(Method method : methods){
        	// The current method name, for easy comparison
        	currentMethodName = method.getName();
        	
        	// Check if the current method is the method we want to call
        	for(String methodPrefix : METHOD_PREFIXES){
        		if(currentMethodName.compareToIgnoreCase(methodPrefix + methodName) == 0){
        			// Check if we have the same parameter count, so we are sure to call the correct method
            		if(method.getParameterCount() == 1){
            			// Initialize parameter list
            			constructorParameters = new ArrayList<>();
            			
            			Class<?>[] parameterTypes = method.getParameterTypes();
            			if(parameterTypes.length < 1) continue; 
            			
            			// Add the parameters, so we can call the method later on
    				    constructorParameters.add(parseObject(object, parameterTypes[0]));

    			        // Call the current method
    			        callMethod(method);
            		}			        		
        		}
        	}
        }
	}
	
	/**
	 * Parses the given string to the given type.
	 * @param value The value to parse as string
	 * @param type The type to parse to
	 * @return The parsed value as object, or NULL in case of an error.
	 */
	public static Object parseValue(String value, Class<?> type){
		if(type == String.class) return value;
		
		if(type == float.class){
    		return Float.parseFloat(value);
		}else if(type == boolean.class){
			return Boolean.parseBoolean(value);
		}else if(type == int.class){
			return Integer.parseInt(value);
		}else if(type == double.class){
			return Double.parseDouble(value);
		}else{
			System.err.println("Unhandled type " + type + ". Cannot parse the value.");
		}
		return null;
	}
	
	public static <T> T parseObject(Object value, Class<T> type){
		return type.cast(value);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Class<T> getClassFromName(String name){
		for(String packageName : PACKAGES){
			try {
				objectClass = Class.forName(packageName + StringUtils.capitalize(name));
				return (Class<T>) objectClass;
			} catch (ClassNotFoundException e) {}
		}
		return null;
	}
}
