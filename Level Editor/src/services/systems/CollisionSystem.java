package services.systems;

import java.util.ArrayList;

import services.ServiceSystem;
import utils.input.mouse.Mouse;
import utils.messaging.IMessageListener;
import utils.messaging.Message;
import utils.messaging.Messenger;

import components.Collider;

public class CollisionSystem extends ServiceSystem<Collider> implements IMessageListener{
	/**
	 * Holds whether or not a collision was detected.
	 */
	private boolean collisionDetected;
	/**
	 * A flag that locks the collision detection. This is required in case of dragging the mouse.
	 */
	private boolean lock;
	
	/**
	 * Default constructor.
	 */
	public CollisionSystem() {
		Messenger.register(this, Message.MOUSE_BUTTON_PRESSED, Message.MOUSE_DRAGGED, Message.MOUSE_BUTTON_RELEASED);
	}
	
	/****************************
	 * InputProcessor Methods	*
	 ****************************/

	/**
	 * This method is called, when a {@see Message}, this class registered for, was triggered.
	 * 
	 * @param message The message that was sent
	 * @param data The additional information that was given
	 */
	@Override
	public void onMessage(Message message, Object... data) {
		// Listen to mouse clicks during mouse movement
		if(Messenger.is(Message.MOUSE_DRAGGED, message) && !lock){
			Messenger.unregister(this, Message.MOUSE_DRAGGED);
			lock = true;
			checkCollision();			
		}else if(Messenger.is(Message.MOUSE_BUTTON_PRESSED, message)){
			lock = true;
			checkCollision();
		}else if(Messenger.is(Message.MOUSE_BUTTON_RELEASED, message)){
			Messenger.register(this, Message.MOUSE_DRAGGED);
			lock = false;
		}
	}
	
	/**
	 * Checks for a collision using the {@see Mouse}.
	 */
	private void checkCollision(){
		collisionDetected = false;
		for(Collider collider : this.objects){
			if(!collider.isActive()) continue;
			
			if(collider.isHit(Mouse.worldCoordinates)){
				collisionDetected = true;
				Messenger.sendMessage(Message.CLICK_COLLISION, collider);
			}
		}
//		for(int i = 0; i < this.objects.size(); i++){
//			// Only use active colliders
//			if(!this.objects.poll().isActive()) continue;
//			
//			// Check collision
//			if(this.objects.get(i).isHit(Mouse.worldCoordinates)){
//				collisionDetected = true;
//				Messenger.sendMessage(Message.CLICK_COLLISION, this.objects.get(i));
//			}
//		}
		
		// Send a message if no collision occurred, but a click did
		if(!collisionDetected) Messenger.sendMessage(Message.CLICK_NO_COLLISION);
	}
	
	/********************
	 * Service Methods	*
	 ********************/
	
	/**
	 * Checks for collision and sends a {@see Message} to all registered listeners in the {@see Messenger}.
	 */
	@Override
	public void update() {
		if(!isActive()) return;
		collisionDetected = false;
		
		for(Collider collider : this.objects){
			if(!collider.isActive()) continue;
			
			for(Collider coll : this.objects){
			
				if(collider.collidesWith(coll)){
					collisionDetected = true;
					Messenger.sendMessage(Message.COLLISION, collider, coll);
				}
			}
		}
		
//		for(int i = 0; i < this.objects.size(); i++){
//			// Only use active colliders
//			if(!this.objects.get(i).isActive()) continue;
//			
//			for (int j = i + 1; j < this.objects.size(); j++) {
//				// Only use active colliders
//				if(!this.objects.get(j).isActive()) continue;
//				
//				if(this.objects.get(i).collidesWith(this.objects.get(j))){
//					collisionDetected = true;
//					Messenger.sendMessage(Message.COLLISION, new Object[]{this.objects.get(i), this.objects.get(j)});
//				}
//			}
//		}
	}
}
