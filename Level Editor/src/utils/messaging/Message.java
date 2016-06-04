package utils.messaging;


/**
 * This enumeration provides all required definitions for every message that shall be sent to registered classes.
 * Use this in combination with the messenger.
 */
public enum Message {
	INVALID_MESSAGE,
	EXIT,
	ANY_KEY,
	Move,
	Scale,
	Rotate,
	CLICK,
	CLICK_UP,
	DRAG,
	CLICK_COLLISION("Click_Collision"),
	CLICK_NO_COLLISION("Click_No_Collision"),
	SINGLE_SELECT,
	MULTI_SELECT,
	COLLISION,
	TOGGLE_PHYSICS,
	CREATE_JOINT,
	MENU_UP,
	MENU_DOWN,
	MENU_SELECT,
	BACK,
	CLONE,
	SAVE_LEVEL,
	MOUSE_DRAGGED,
	MOUSE_ENTERED("Mouse_Entered"),
	MOUSE_EXITED("Mouse_Exited"),
	MOUSE_CLICKED,
	MOUSE_MOVED,
	MOUSE_LEFT_CLICKED,
	MOUSE_RIGHT_CLICKED,
	MOUSE_MIDDLE_CLICKED,
	MOUSE_EXTRA_CLICKED,
	MOUSE_BUTTON_PRESSED("Mouse_Button_Pressed"),
	MOUSE_BUTTON_RELEASED("Mouse_Button_Released"),
	QUIT,
	RESIZE,
	RESIZE_FRAME,
	DROPPED,
	TERMINATE_THREAD("Dropping_Finished"),
	KEY_DOWN,
	KEY_UP,
	UNHANDLED_KEY_STATE;
	
	/**
	 * The value of the message.
	 */
	private String value = "";
	
	/**
	 * Default constructor.
	 */
	private Message() {}
	
	/**
	 * Constructor.
	 * @param value The value of the message
	 */
	private Message(String value){
		this.value = value;
	}

	/**
	 * @return The value of the message as defined, or the name, if no value was defined.
	 */
	public String getValue(){
		return (this.value.isEmpty()) ? this.name() : this.value;
	}
}
