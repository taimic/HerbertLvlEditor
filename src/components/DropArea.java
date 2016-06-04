package components;

import java.util.Map.Entry;

import scenes.IScene;
import scenes.Scene;
import services.ServiceLocator;
import services.systems.CollisionSystem;
import services.systems.EditorSystem;
import services.systems.EntitySystem;
import services.systems.SceneSystem;
import utils.input.mouse.Mouse;
import utils.math.Vector;
import utils.messaging.IMessageListener;
import utils.messaging.Message;
import utils.messaging.Messenger;
import utils.reflection.ClassInspector;
import components.drawables.Sprite;
import entity.Entity;
import factories.CopyFactory;

public class DropArea extends Component implements IMessageListener{
	/**
	 * The scene of the drop area.
	 */
	private Scene scene;
	/**
	 * The count of the entities that were already created.
	 */
	private static int entityCount;
	
	/**
	 * Default constructor.
	 */
	public DropArea() {
		Messenger.register(this, Message.DROPPED);
		scene = ServiceLocator.getService(SceneSystem.class).getLastAddedObject();
	}
	
	/**
	 * Drops the entity by copying it with the specified positions.
	 * @param entity The {@see Entity} to copy
	 * @param position The position to spawn the entity at
	 * @return The {@see Component} itself, for chaining.
	 */
	public Component drop(IScene scene, Entity entity, Vector position){
		// Only add entity to another scene
		if(this.scene != scene) return this;
		
		// Copy entity
		Entity copiedEntity = null;
		try {
			copiedEntity = CopyFactory.copy(entity, Entity.class);
		} catch (Exception e) {
			System.err.println("Could not copy entity: " + entity.getName());
			e.printStackTrace();
			return this;
		}
		
		// Set position
		Transform transform = copiedEntity.getComponent(Transform.class);
		transform.setPosition(position.copy());
		
		// Re-read sprite (since it is not serializable)
		Sprite sprite = copiedEntity.getComponent(Sprite.class);
		if(sprite != null) sprite.loadImage();
		
		// De-select all currently selected entities
		ServiceLocator.getService(EditorSystem.class).deselectEditables();
		
//		ClassInspector.printParameterValues(copiedEntity, true);
		
		// Update component values
		for(Entry<Class<? extends Component>, Component> entry : copiedEntity.getComponents().entrySet()){
			entry.getValue().setEntity(copiedEntity);
			if(entry.getValue() instanceof Collider) ServiceLocator.getService(CollisionSystem.class).add((Collider) entry.getValue());
			if(entry.getValue() instanceof Editable) ServiceLocator.getService(EditorSystem.class).add((Editable) entry.getValue());
			
			// DEBUG ONLY
			ClassInspector.printParameterValues(entry.getValue(), true);
		}
		
//		System.out.println(transform.getPosition());
		
		// Add entity
		this.scene.addEntity(copiedEntity.setName(copiedEntity.getName() + " " + entityCount));
		ServiceLocator.getService(EntitySystem.class).add(copiedEntity);
		entityCount++;
		
		return this;
	}

	/**
	 * Handles the messages.
	 */
	@Override
	public void onMessage(Message message, Object... data) {
		if(Messenger.is(Message.DROPPED, message)) drop((IScene) data[0], (Entity) data[1], (Vector) data[2]);
	}

}
