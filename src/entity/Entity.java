package entity;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import services.ServiceLocator;
import services.systems.EntitySystem;
import components.Component;

public class Entity implements Serializable{
	/**
	 * The serialization ID.
	 */
	private static final long serialVersionUID = 4822961177044625554L;
	// Holds all the components
	private Map<Class<? extends Component>, Component> components;
	// The name of the entity
	private String name;
	// Holds whether or not the entity is active
	private boolean active;
	
	// Default constructor
	public Entity() {
		this.name = "";
		components = new LinkedHashMap<Class<? extends Component>, Component>();
		ServiceLocator.getService(EntitySystem.class).add(this);
	}
	
	// Creates an entity with the given name
	public Entity(String name){
		this();
		this.name = name;
	}
	
	// Adds the given component, if not added before
	public Entity addComponent(Component component){
		Class<? extends Component> key = component.getClass();
		if(!components.containsKey(key)) {
			component.setEntity(this);
			components.put(key, component);
		}
		return this;
	}
	
	// Removes the given component, if added before
	public Entity removeComponent(Component component){
		Class<? extends Component> key = component.getClass();
		if(components.containsKey(key)){
			component.setEntity(null);
			components.remove(key, component);
		}
		return this;
	}
	
	// Updates the entity (and therefore its components)
	public void update(){
		for (Entry<Class<? extends Component>, Component> entry : components.entrySet()) {			
			entry.getValue().update();
			entry.getValue().setActive(this.active);
		}
	}
	
	// Returns the component with the given type, or NULL
	public <T extends Component> T getComponent(Class<T> type){
		if(components.containsKey(type)) return type.cast(components.get(type));
		return null;
	}
	
	// Removes all components
	public Entity removeComponents(){
		components.clear();
		return this;
	}
	
	// Sets the components to the given ones
	public Entity setComponents(Map<Class<? extends Component>, Component> components){
		this.components = components;
		return this;
	}
	
	// Returns the component with the given type, or NULL
	public <T extends Component> T getComponentFromSuperClass(Class<T> type){
		if(components.containsKey(type)) return type.cast(components.get(type));
		for (Component component : components.values()) {
			if(type.isAssignableFrom(component.getClass())) return type.cast(component);
		}
		return null;
	}
	
	// Returns all currently available components
	public Map<Class<? extends Component>, Component> getComponents(){
		return this.components;
	}
	
	// Sets the name of the entity
	public Entity setName(String name){
		this.name = name;
		return this;
	}
	
	// Returns the name of the entity
	public String getName(){
		return this.name;
	}
	
	// Activates the entity
	public Entity activate(){
		this.active = true;
		return this;
	}
	
	// Deactivates the entity
	public Entity deactivate(){
		this.active = false;
		return this;
	}
	
	// Sets the entity in-/active
	public Entity setActive(boolean active){
		this.active = active;
		return this;
	}
	
	// Sets the components of the entity in-/active
	public Entity setCompononentsActive(boolean active){
		for (Entry<Class<? extends Component>, Component> entry : components.entrySet()){
			entry.getValue().setActive(active);
		}
		return this;
	}
	
	// Returns whether or not the entity is active
	public boolean isActive(){
		return this.active;
	}

	/****************************
	 * 	Serialization Methods	*
	 ****************************/
	
//	@Override
//	public void write(Json json) {
//		json.writeValue("name", name);
//		json.writeValue("active", active);
//		json.writeValue("components", components);
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public void read(Json json, JsonValue jsonData) {
//		name = jsonData.getString("name");
//		active = jsonData.getBoolean("active");
//		components = json.readValue("components", HashMap.class, jsonData);
//		
//		// Helper variable
//		Map<Class<? extends Component>, Component> validComponents = new HashMap<Class<? extends Component>, Component>();
//		
//		// Validate the keys
//		for (Entry<Class<? extends Component>, Component> entry : components.entrySet()){	
//			try {
//				entry.getValue().setEntity(this);
//				validComponents.put((Class<? extends Component>) Class.forName("" + entry.getKey()), entry.getValue());
//			} catch (ClassNotFoundException cnfe) {
//				cnfe.printStackTrace();
//			}
//		}
//		
//		// Set the valid components
//		components = validComponents;
//	}
}
