package statemachine;


public interface IState {
	/**
	 * Is executed when the state is entered.
	 */
	void enter();
	
	/**
	 * Is executed when the state is exited.
	 */
	void exit();
	
	/**
	 * Is executed when the state is updated.
	 */
	void update();
}
