package statemachine.state;

import java.awt.Color;
import java.util.ArrayList;

import services.ServiceLocator;
import services.systems.EditorSystem;
import statemachine.IState;
import utils.input.KeyInputListener;
import utils.input.mouse.Mouse;
import utils.math.Vector;
import utils.messaging.IMessageListener;
import utils.messaging.Message;
import utils.messaging.Messenger;
import components.Collider;
import components.Editable;
import components.Transform;

public enum EditableSelectionState implements IState, IMessageListener {
	/**
	 * This state handles the selection for exactly one {@see Editable}.
	 */
	Editor_Single_Select() {
		@Override
		public void enter() {
			super.enter();
			// If editable is set, de-select all others and select the current editable
			if(clickedEditable != null){
				ServiceLocator.getService(EditorSystem.class).deselectEditables();
				clickedEditable.setSelected(true);
				updateColliderPositions();
				clickedEditable = null;
			}
		}
	},
	
	/**
	 * This state handles the selection for multiple {@see Editable}s.
	 */
	Editor_Multi_Select() {
		@Override
		public void enter() {
			if(clickedEditable != null){
				clickedEditable.setSelected(!clickedEditable.isSelected());
				updateColliderPositions();
				clickedEditable = null;
			}
		}
	},
	
	/**
	 * This state handles the occurrence of a collision when the mouse was clicked.
	 */
	Click_Collision(){
		@Override
		public void enter() {
			super.enter();
			// Retrieve information about the click
			clickedCollider = (Collider) extraInfo[0];
			clickedEditable = clickedCollider.getEntity().getComponent(Editable.class);

			// Update required variables
			mouseClickWorldPosition = Mouse.worldCoordinates.copy();
			clickedEditable.setTintColor(Color.GREEN);
			
			// Return to last state
			EditorSystem.selectionTypeStateMachine.revert();
		}
	},
	
	/**
	 * This state handles the de-selection, which happens when a mouse click occurred, but no collision occured.
	 */
	Click_No_Collision(){
		@Override
		public void enter() {
			// Update required variables
			clickedEditable = null;
			ServiceLocator.getService(EditorSystem.class).deselectEditables();
			mouseClickWorldPosition = Mouse.worldCoordinates.copy();
			selectedEditables = new ArrayList<Editable>();
			colliderPositions = new ArrayList<Vector>();
			
			// Return to last state
			EditorSystem.selectionTypeStateMachine.revert();
		}
	};

	/**
	 * The default implementation for entering a state.
	 */
	@Override
	public void enter() {
		Messenger.register(this, Message.CLICK_COLLISION, Message.CLICK_NO_COLLISION);
	}
	
	/**
	 * The default implementation for updating a state.
	 */
	@Override
	public void update() {
//		System.out.println("jdhjbasdhjsa");
		if(KeyInputListener.getInstance().isAnyKeyDown()){
//			System.out.println("DOWN!!!!");
		}
		
	}

	/**
	 * The default implementation for exiting a state.
	 */
	@Override
	public void exit() {
		Messenger.unregister(this, Message.CLICK_COLLISION, Message.CLICK_NO_COLLISION);
	}
	
	@Override
	public void onMessage(Message message, Object... data) {
		extraInfo = data;
		EditorSystem.selectionTypeStateMachine.changeState(Messenger.getStateFromMessage(this.getDeclaringClass(), message));
	}
	
	// The editable that was selected
	protected static Editable clickedEditable;
	// The collider of the clicked editable
	protected static Collider clickedCollider;
	// Holds the extra info of the message for further access
	protected static Object[] extraInfo;
	
	// The mouse click world position
	public static Vector mouseClickWorldPosition = new Vector();
	// Holds the collider positions of all clicked editables
	public static ArrayList<Vector> colliderPositions = new ArrayList<Vector>();
	// Holds all selected editables
	public static ArrayList<Editable> selectedEditables;
	
	// TODO: Put this code somewhere else (it does not belong here...; maybe in ColliderSystem)
	
	// Updates the collider positions (in terms of the references to the positions)
	protected void updateColliderPositions(){
		colliderPositions.clear();
		selectedEditables = ServiceLocator.getService(EditorSystem.class).getSelectedEditables();
		for(Editable editable: selectedEditables){
			colliderPositions.add(editable.getEntity().getComponent(Transform.class).getPosition());
		}
	}
}
