package factories;

import java.awt.Color;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import camera.Camera;

public class CameraFactory {
	// The package of the components, which is required to find the classes
	public static final String COMPONENT_PACKAGE = "camera.";
	// The prefix for the method we want to check for
	public static final String METHOD_PREFIX = "set";
	
	// The components class
	private static Class<?> componentClass;
	// The current component we want to create
	private static Camera currentComponent;
	// The methods of the current class
	private static Method[] methods;
	// The parameters of the constructor according to the class definition
	private static ArrayList<Object> constructorParameters;
	// The name of the current method to check for
	private static String currentMethodName;
	
	// Creates an empty component of the given type
	public static Camera createDefault(Class<?> componentType){
		componentClass = componentType;
		if(instantiateComponent()) return currentComponent;
		return null;
	}

	// Creates an empty component of the given type
	public static Camera createDefault(String componentType){
		if(getComponentClassFromString(componentType)){
			if(instantiateComponent()) return currentComponent;
		}
		return null;
	}
	
	// Retrieves all methods of the current class
	public static void retrieveMethods(){
		methods = componentClass.getMethods();
	}
	
	// Tries to create the component
	private static boolean instantiateComponent(){
		// Create the component from the current class
		try {
			currentComponent = (Camera) componentClass.getConstructor().newInstance();
			return true;
		} catch (InstantiationException ie) {
			System.err.println("Could not instantiate component with the class: " + componentClass);
			ie.printStackTrace();
		} catch (IllegalAccessException iae) {
			iae.printStackTrace();
		} catch (IllegalArgumentException e) {
			System.err.println("Invalid constructor arguments for class: " + componentClass);
			e.printStackTrace();
		} catch (InvocationTargetException ite) {
			ite.printStackTrace();
		} catch (NoSuchMethodException nsme) {
			System.out.println("Constructor not found for class: " + componentClass);
			nsme.printStackTrace();
		} catch (SecurityException se) {
			System.err.println("Constructor not accessible for class: " + componentClass);
			se.printStackTrace();
		}
		return false;
	}	
	
	// Tries to invoke the given method
	private static boolean callMethod(Method method){
		try {
			method.invoke(currentComponent, constructorParameters.toArray());
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
	
	// Updates the component values
	public static void updateComponentValues(String[] currentParameterValues, String methodName){
		// Loop through all methods of the class
        for(Method method : methods){
        	// The current method name, for easy comparison
        	currentMethodName = method.getName();
        	
        	// Check if the current method is the method we want to call
        	if(currentMethodName.compareToIgnoreCase(METHOD_PREFIX + methodName) == 0){
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
	
	/**
	 * Parses the given string to the given type.
	 * @param value The value to parse as string
	 * @param type The type to parse to
	 * @return The parsed value as object, or NULL in case of an error.
	 */
	public static Object parseValue(String value, Class<?> type){
		if(type == String.class) return value;
		if(type == Color.class){
			Color color;
			try {
			    Field field = Color.class.getField(value.replace("Color.", ""));
			    color = (Color)field.get(null);
			} catch (Exception e) {
			    color = null; // Not defined
			}
			return color;
		}
		
		if(type == float.class){
    		return Float.parseFloat(value);
		}else if(type == boolean.class){
			return Boolean.parseBoolean(value);
		}else if(type == int.class){
			return Integer.parseInt(value);
		}else if(type == double.class){
			return Double.parseDouble(value);
		}else{
			System.err.println("Unhandled type " + type + " in the composer.");
		}
		return null;
	}
	
	// Tries to retrieve the class according to the current component's type
	public static boolean getComponentClassFromString(String componentType){
		try {
			componentClass = Class.forName(COMPONENT_PACKAGE + componentType);
			return true;
		} catch (ClassNotFoundException cnfe) {
			if(componentType.contains("drawables.")) return false;
			boolean status = getComponentClassFromString("drawables." + componentType);
			if(!status){
				System.err.println("Could not find the class for component type " + componentType);
				cnfe.printStackTrace();
			}
			return status;
		}
	}
}

