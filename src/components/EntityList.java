package components;

import java.util.ArrayList;
import java.util.List;

import utils.math.Vector;
import utils.messaging.IMessageListener;
import utils.messaging.Message;
import utils.messaging.Messenger;
import components.drawables.Sprite;
import entity.Entity;

public class EntityList extends Component implements IMessageListener{
	/**
	 * Holds all the entities.
	 */
	private List<Entity> entities;
	/**
	 * The last coordinates, for auto-positioning.
	 */
	private Vector lastCoordinates;
	/**
	 * The width ratio.
	 */
	private float wr = 1;
	/**
	 * The height ratio.
	 */
	private float hr = 1;
	
	/**
	 * Default constructor.
	 */
	public EntityList() {
		entities = new ArrayList<>();
		lastCoordinates = Vector.getZeroVector(3);
		Messenger.register(this, Message.RESIZE);
	}
	
	/**
	 * Adds an entity to the list.
	 * @param entity The entity to add
	 */
	public void addEntity(Entity entity){
		this.entities.add(entity);
	}
	
	/**
	 * Updates the positions of the entities.
	 */
	@Override
	public Component update(){
		Sprite sprite;
		for(Entity entity : entities){
			sprite = entity.getComponent(Sprite.class);
			if(sprite == null) continue;
			
			entity.getComponent(Transform.class).setPosition(this.getEntity().getComponent(Transform.class).getPosition().add(lastCoordinates).multiplyComponentwise(new Vector(wr, hr, 1)));
			lastCoordinates = lastCoordinates.add(sprite.getOriginalWidth(), 0, 0).add(10, 0, 0);
		}
		lastCoordinates = Vector.getZeroVector(lastCoordinates.getDimension());
		return this;
	}
	
	/**
	 * Handles the resize message.
	 */
	@Override
	public void onMessage(Message message, Object... data) {
		this.wr = (float)data[0];
		this.hr = (float)data[1];
	}
}
