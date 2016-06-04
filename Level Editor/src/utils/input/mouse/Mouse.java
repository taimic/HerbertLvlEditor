package utils.input.mouse;

import java.awt.event.MouseEvent;

import utils.math.Vector;
import utils.messaging.IMessageListener;
import utils.messaging.Message;
import utils.messaging.Messenger;

public class Mouse implements IMessageListener{
	// Holds the screen coordinates of the mouse position
	public static Vector screenCoordinates = Vector.getZeroVector(3);
	// Holds the window coordinates of the mouse position
	public static Vector windowCoordinates = Vector.getZeroVector(3);
	// Holds the world coordinates of the mouse position
	public static Vector worldCoordinates = Vector.getZeroVector(3);
	// Holds whether or not the mouse is moving
	public static boolean isMoving;
	// Holds whether or not the mouse is being dragged
	public static boolean isDragging;
	// Holds whether or not any button was released
	public static boolean isAnyButtonUp;
	// Holds whether or not any button was pressed
	public static boolean isAnyButtonDown;
	// Holds whether or not the left mouse button is down
	public static boolean isLeftDown;
	// Holds whether or not the right mouse button is down
	public static boolean isRightDown;
	// Holds whether or not the middle mouse button is down
	public static boolean isMiddleDown;
	// The mouse event that occurred
	private MouseEvent mouseEvent;
	private static Mouse instance;
	
	// Default constructor
	public Mouse() {
		Messenger.register(this, Message.MOUSE_MOVED, Message.MOUSE_DRAGGED, Message.MOUSE_BUTTON_RELEASED);
	}
	
	public static Mouse getInstance(){
		if(instance == null) instance = new Mouse();
		return instance;
	}

	@Override
	public void onMessage(Message message, Object... data) {
		isDragging = Messenger.is(message, Message.MOUSE_DRAGGED) && !Messenger.is(message, Message.MOUSE_BUTTON_RELEASED);
		isMoving = isDragging || Messenger.is(message, Message.MOUSE_MOVED);
		mouseEvent = (MouseEvent) data[0];
		screenCoordinates = new Vector(mouseEvent.getXOnScreen(), mouseEvent.getYOnScreen(), 0);
		windowCoordinates = new Vector(mouseEvent.getX(), mouseEvent.getY(), 0);
		// TODO: use scene camera to calculate the coordinates within the scene (where the scene equals the world)
		worldCoordinates = new Vector(mouseEvent.getX(), mouseEvent.getY(), 0);//ServiceLocator.getService(CameraSystem.class).screenToWorld(screenCoordinates);
	}
}
