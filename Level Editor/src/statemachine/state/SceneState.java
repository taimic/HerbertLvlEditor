package statemachine.state;

import java.awt.event.MouseEvent;
import java.util.List;

import scenes.IScene;
import scenes.Scene;
import services.ServiceLocator;
import services.systems.EditorSystem;
import statemachine.IState;
import utils.input.mouse.Mouse;
import utils.messaging.IMessageListener;
import utils.messaging.Message;
import utils.messaging.Messenger;

import components.Editable;

public enum SceneState implements IState, IMessageListener {
	/**
	 * This state does nothing, but signaling that the scene is active, i.e. ready for dropping entities on it.
	 */
	Mouse_Entered(){
		@Override
		public void enter() {
			super.enter();
			if(mouseEvent != null) dropReadyScene = ((IScene)mouseEvent.getComponent());
		}
		
		@Override
		public void update() {
			if(!Mouse.isDragging) dropReadyScene = null;
		}
	},
	
	/**
	 * This state does nothing, but signaling that the scene is inactive - in terms of interaction.
	 */
	Mouse_Exited(){
		@Override
		public void enter() {
			super.enter();
			dropReadyScene = null;
		}
	},
	
	/**
	 * In this state the dropped entities will be added to the scene.
	 */
	Mouse_Button_Released(){

		@Override
		public void enter() {
			super.enter();
			if(dropReadyScene != null){
				new Thread(new Runnable() {
					IScene currentReadyScene = dropReadyScene;
					List<Editable> editables = ServiceLocator.getService(EditorSystem.class).getSelectedEditables();
					
					@Override
					public void run() {
						for(Editable editable : editables){
							Messenger.sendMessage(Message.DROPPED, currentReadyScene, editable.getEntity(), Mouse.windowCoordinates);
						}
					}
				}).start();
			}
			
			Scene.stateMachine.revert();
		}
	};
	
	/**
	 * The default enter implementation.
	 */
	@Override
	public void enter() {
		Messenger.register(this, Message.MOUSE_ENTERED, Message.MOUSE_EXITED, Message.MOUSE_BUTTON_RELEASED);
	}

	/**
	 * The default exit implementation.
	 */
	@Override
	public void exit() {
		Messenger.unregister(this, Message.MOUSE_ENTERED, Message.MOUSE_EXITED, Message.MOUSE_BUTTON_RELEASED);
	}

	/**
	 * The default update implementation.
	 */
	@Override
	public void update() {}
	
	/**
	 * Changes the state according to the received message.
	 */
	@Override
	public void onMessage(Message message, Object... data) {
		mouseEvent = (MouseEvent)data[0];
		Scene.stateMachine.changeState(Messenger.getStateFromMessage(this.getDeclaringClass(), message));		
	}
	
	/**
	 * The mouse event that occurred.
	 */
	protected static MouseEvent mouseEvent;
	/**
	 * The scene that is currently set drop-ready.
	 */
	protected static IScene dropReadyScene;
}