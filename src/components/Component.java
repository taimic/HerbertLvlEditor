package components;

import java.io.Serializable;

import entity.Entity;

public abstract class Component implements Serializable{
	/**
	 * The serialization ID.
	 */
	private static final long serialVersionUID = -7794906730014898597L;
	
	// The reference to the entity
	protected transient Entity entity;
	// Holds whether or not the component is active
	protected boolean active;
	// The original width
	protected float originalWidth;
	// The original height
	protected float originalHeight;
	
	// Default constructor
	public Component() {}
	
	// Activates the component
	public Component activate(){
		active = true;
		return this;
	}
	
	// Deactivates the component
	public Component deactivate(){
		active = false;
		return this;
	}
	
	// Returns whether or not the component is active
	public boolean isActive(){
		return active;
	}
	
	// Sets the component'S active state
	public Component setActive(boolean active){
		this.active = active;
		return this;
	}
	
	// Updates the component
	public Component update(){
		return this;
	}
	
	// Returns the entity of the component
	public Entity getEntity(){
		return entity;
	}
	
	// Sets the entity of the component
	public Component setEntity(Entity entity){
		this.entity = entity;
		return this;
	}
	
	// Returns the component with the given class in the entity of this component
	public <T extends Component> T getComponentFromEntity(Class<T> type){
		return getEntity().getComponent(type);
	}
	
	/**
	 * Resizes the component. Override this method, if an implementation is required.
	 * @param width The new width
	 * @param height The new height
	 * @return The {@see Component} itself, for chaining.
	 */
	public Component resize(float width, float height){
		return this;
	}
	
	/**
	 * @return The original width.
	 */
	public float getOriginalWidth(){
		return this.originalWidth;
	}
	
	/**
	 * @return The original height.
	 */
	public float getOriginalHeight(){
		return this.originalHeight;
	}
}
