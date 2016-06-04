package factories;

import java.util.ArrayList;
import java.util.Map;

import components.Component;

import entity.Entity;

public class EntityFactory extends Factory{
	/**
	 * Holds all the loaded entities.
	 */
	private static Map<String, Object> entities;
	
	/**
	 * Loads the entities from the file with the given name.
	 * @param filename The file's name
	 */
	public static void load(String filename){
		entities = load(filename, Entity.class);
	}
	
	// Returns the entity with the given name
	public static Entity getEntity(String name){
		return get(name, entities, Entity.class);
	}
	
	// Returns an empty entity with the given name
	public static Entity createEmpty(String name){
		return create(name, Entity.class);
	}
	
	// Returns an empty entity with no name
	public static Entity createEmpty(){
		return create(Entity.class);
	}
	
	// Returns a new entity with the given name and adds the given components
	public static Entity createEntity(String name, ArrayList<Component> components){
		// Create entity
		Entity entity = createEmpty(name);
		
		// Add components to the created entity
		for (Component component : components) {
			entity.addComponent(component);
		}
		
		// Activate the entity
		entity.activate();
		
		return entity;
	}
	
	// Creates the entity and sets it active immediately
	public static Entity createEntityActive(String name, ArrayList<Component> components){
		return createEntity(name, components).activate();
	}
	
	// Returns a new entity without a name and adds the given components
	public static Entity createEntity(ArrayList<Component> components){
		return createEntity("", components);
	}
	
	// Returns a new entity with the given name and adds the given component
	public static Entity createEntity(String name, Component component){
		// Create entity
		Entity entity = createEmpty(name);
		
		// Add component
		entity.addComponent(component);
		
		return entity;
	}
	
	// Returns a new entity without a name and adds the given component
	public static Entity createEntity(Component component){
		return createEntity("", component);
	}
}
