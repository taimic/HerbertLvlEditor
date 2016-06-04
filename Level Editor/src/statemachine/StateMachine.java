package statemachine;

/**
 * This class provides the implementation for a state machine.
 */
public class StateMachine {
	/**
	 * The current state.
	 */
	private IState currentState;
	/**
	 * The last state.
	 */
	private IState lastState;
	
	/**
	 * Constructor.
	 * @param defaultState The default state that is set right from the beginning
	 */
	public StateMachine(IState defaultState) {
		changeState(defaultState);
	}
	
	/**
	 * Changes the state from the current one to the given one.
	 * @param changeTo The state to change to
	 * @return The state machine itself, for chaining.
	 */
	public StateMachine changeState(IState changeTo){
		if(changeTo != null && !isCurrentState(changeTo)) {
			if(currentState != null) currentState.exit();
			lastState = currentState;
			currentState = changeTo;
			changeTo.enter();
		}
		return this;
	}
	
	/**
	 * Changes back to the last state.
	 * @return The state machine itself, for chaining.
	 */
	public StateMachine revert(){
		return changeState(lastState);
	}
	
	/**
	 * Updates the state machine, therefore its states.
	 * @return The state machine itself, for chaining.
	 */
	public StateMachine update(){
		currentState.update();
		return this;
	}
	
	/**
	 * @param state The state to check
	 * @return Whether or not the given state is the current state.
	 */
	public boolean isCurrentState(IState state){
		return this.currentState != null && this.currentState.equals(state);
	}
}
