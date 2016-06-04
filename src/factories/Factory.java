package factories;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import persistance.XMLComposer;

public class Factory {	
	/**
	 * Loads the entities from the file with the given name.
	 * @param filename The file's name
	 */
	@SuppressWarnings("unchecked")
	public static <T> Map<String, Object> load(String filename, Class<T> type){
		String composerClassName = type.getSimpleName() + "Composer";
		Class<? extends XMLComposer<?>> composerClass;
		try {
			composerClass = (Class<? extends XMLComposer<?>>) Class.forName("persistance." + composerClassName);
			XMLComposer<?> xmlComposer = composerClass.getDeclaredConstructor(String.class).newInstance(filename);
			return (Map<String, Object>) xmlComposer.compose().getData();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			System.err.println("Error while loading XML " + filename);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Creates a new object with the given type with no name.
	 * @param type The type of the object
	 * @return The created object of the given type, or NULL.
	 */
	public static <T> T create(Class<T> type){
		return create("", type);
	}
	
	/**
	 * Creates a new object with the given type and name.
	 * @param type The type of the object
	 * @return The created object of the given type, or NULL.
	 */
	public static <T> T create(String name, Class<T> type){
		try {
			return type.getDeclaredConstructor(String.class).newInstance(name);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			System.err.println("Could not create empty object of type " + type.getSimpleName() + " using the name '" + name + "'.");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Creates a new default object, i.e. only calling the default constructor. So make sure there is a default constructor available.
	 * @param type The type of the object
	 * @return The created object of the given type, or NULL.
	 */
	public static <T> T createDefault(Class<T> type){
		try {
			return type.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			System.err.println("Could not create default object of type " + type.getSimpleName());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @param name The name of the object to get.
	 * @param type The type of the object
	 * @return The object itself, or NULL.
	 */
	public static <T> T get(String name, Map<String, Object> data, Class<T> type){
		return type.cast(data.get(name));
	}
}
