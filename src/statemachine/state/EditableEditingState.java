package statemachine.state;

import java.awt.event.KeyEvent;

import services.ServiceLocator;
import services.systems.EditorSystem;
import statemachine.IState;
import utils.input.mouse.Mouse;
import utils.input.mouse.MouseAppearance;
import utils.math.Vector;
import utils.messaging.IMessageListener;
import utils.messaging.Message;
import utils.messaging.Messenger;
import components.Editable;
import components.Transform;

public enum EditableEditingState implements IState, IMessageListener {
	/**
	 * The idle state for the {@see Editable}.
	 */
	Idle() {},
	
	/**
	 * The move state for the {@see Editable}.
	 */
	Move() {		
		@Override
		public void update() {
			// If mouse is not being dragged, no update is necessary
			if(!Mouse.isDragging) return;
			
			for(Editable editable : ServiceLocator.getService(EditorSystem.class).getSelectedEditables()){
				editable.getEntity().getComponent(Transform.class).setPosition(Mouse.worldCoordinates.copy());//.sub(SelectionTypeState.mouseClickWorldPosition).add(SelectionTypeState.colliderPositions.get(cnt)));
			}
		}
	},
	
	/**
	 * The rotate state for the {@see Editable}.
	 */
	Rotate() {
		@Override
		public void update() {
			// If mouse is not being dragged, no update is necessary
			if(!Mouse.isDragging) return;
			
			for(Editable editable : ServiceLocator.getService(EditorSystem.class).getSelectedEditables()){
				editable.getEntity().getComponent(Transform.class).setRotation(editable.getEntity().getComponent(Transform.class).getRotation().add(0, 0, -Math.signum(lastMouseWorldPosition.get(Vector.Y) - Mouse.worldCoordinates.get(Vector.Y)) * rotationAngle));	
			}
			
			// Update all selected editables
//			for(Editable editable: SelectionTypeState.selectedEditables){
//				// Set the rotation
//				editable.getEntity().getComponent(Transform.class).getRotation().set(editable.getEntity().getComponent(Transform.class).getRotation().cpy().add(0, 0, -Math.signum(lastMouseWorldPosition.y - Mouse.worldCoordinates.y) * rotationAngle));
//			}

			// Update last mouse world position
			lastMouseWorldPosition = Mouse.worldCoordinates.copy();
		}
	},
	
	/**
	 * The scale state for the {@see Editable}.
	 */
	Scale() {
		@Override
		public void update() {
			// If mouse is not being dragged, no update is necessary
			if(!Mouse.isDragging) return;
			
			for(Editable editable : ServiceLocator.getService(EditorSystem.class).getSelectedEditables()){
				float currentScale = (float) (-Math.signum(lastMouseWorldPosition.get(Vector.Y) - Mouse.worldCoordinates.get(Vector.Y)) * scale);
				editable.getEntity().getComponent(Transform.class).setScale(editable.getEntity().getComponent(Transform.class).getScale().add(currentScale, currentScale, 0));
			}
			
			// Update last mouse world position
			lastMouseWorldPosition = Mouse.worldCoordinates.copy();
		}
	};
	
	/**
	 * The default enter method.
	 */
	@Override
	public void enter() {
		Messenger.register(this, Message.KEY_DOWN, Message.Move, Message.Scale, Message.Rotate);
	}
	
	/**
	 * The default update method.
	 */
	@Override
	public void update() {}

	/**
	 * The default exit method.
	 */
	@Override
	public void exit() {
		Messenger.unregister(this, Message.KEY_DOWN, Message.Move, Message.Scale, Message.Rotate);
	}

	/**
	 * Enters the state according to the received message.
	 */
	@Override
	public void onMessage(Message message, Object... data) {
		if(Messenger.is(Message.KEY_DOWN, message)){
			if((int)data[0] == KeyEvent.VK_W) Messenger.sendMessage(Message.Move);
			else if((int)data[0] == KeyEvent.VK_E) Messenger.sendMessage(Message.Scale);
			else if((int)data[0] == KeyEvent.VK_R) Messenger.sendMessage(Message.Rotate);
			return;
		}
		EditorSystem.editingTypeStateMachine.changeState(Messenger.getStateFromMessage(this.getDeclaringClass(), message));
	}
	
	// The last position of the mouse in world coordinates
	protected Vector lastMouseWorldPosition = Vector.getZeroVector(3);
	
	// TODO: read from config file
	// Holds the rotation angle
	protected float rotationAngle = 1.3f;
	// The scale we want to add/subtract
	protected float scale = 0.1f;
}
