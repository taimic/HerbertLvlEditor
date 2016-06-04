package utils.reflection;

import java.lang.reflect.Field;

public class ClassInspector {
	/**
	 * The operating system dependent line separator.
	 */
	private static final String NL = System.getProperty("line.separator");
	
	public static <T> String getParameterValues(T object, boolean ignoreAccessModifier){
		String values = "";
		
		Field[] fields = object.getClass().getDeclaredFields();
		
		for(Field field : fields){
			try {
				field.setAccessible(ignoreAccessModifier);
				values += field.getName() + ": " + field.get(object) + "," + NL;
			} catch (IllegalArgumentException | IllegalAccessException e) {}
		}
		
		return values;
	}
	
	public static <T> void printParameterValues(T object, boolean ignoreAccessModifier){
		System.out.println(object.getClass().getSimpleName() + "{" + NL + getParameterValues(object, ignoreAccessModifier) + "}");
	}
}
