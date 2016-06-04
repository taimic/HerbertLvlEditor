package services.systems;

import java.util.ArrayList;

import services.ServiceSystem;
import statemachine.StateMachine;
import statemachine.state.EditableEditingState;
import statemachine.state.EditableSelectionState;
import components.Editable;

public class EditorSystem extends ServiceSystem<Editable>{
	/**
	 * The state machine that is used for selecting {@see Editable}s.
	 */
	public static StateMachine selectionTypeStateMachine;
	/**
	 * The state machine that is used for editing  {@see Editable}s.
	 */
	public static StateMachine editingTypeStateMachine;
	/**
	 * Holds all selected {@see Editable}s.
	 */
	private ArrayList<Editable> selectedEditables;
	
	/**
	 * Default constructor.
	 */
	public EditorSystem() {
		selectedEditables = new ArrayList<Editable>();
		
		selectionTypeStateMachine = new StateMachine(EditableSelectionState.Editor_Single_Select);
		editingTypeStateMachine = new StateMachine(EditableEditingState.Idle);
	}
	
	/**
	 * De-selects all {@see Editable}s.
	 * @return The {@see IServiceSystem} for chaining.
	 */
	public EditorSystem deselectEditables(){
		for (Editable editable : selectedEditables) {
			editable.setSelected(false);
		}
		return this;
	}
	
	/**
	 * @return All selected {@see Editable}s.
	 */
	public ArrayList<Editable> getSelectedEditables(){
		updateSelectedEditables();
		return selectedEditables;
	}
	
	/**
	 * Updates the selected {@see Editable}s list.
	 * @return The {@see IServiceSystem} for chaining.
	 */
	private EditorSystem updateSelectedEditables(){
		selectedEditables.clear();
		for (Editable editable : this.objects) {
			if(editable.getEntity() != null && editable.isSelected() && editable.isActive()) selectedEditables.add(editable);
		}
		return this;
	}
	
	/********************
	 * Service Methods	*
	 ********************/

	/**
	 * Updates the state machines.
	 */
	@Override
	public void update() {
		selectionTypeStateMachine.update();
		editingTypeStateMachine.update();
		
		for(Editable editable : this.objects){
			if(editable.isActive()) editable.update();
		}
	}
	
	/**
	 * Disposes the {@see IServiceSystem}.
	 */
	@Override
	public EditorSystem clear(){
		super.dispose();
		selectedEditables.clear();
		return this;
	}
}
