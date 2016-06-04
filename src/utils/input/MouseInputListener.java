package utils.input;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

import utils.messaging.Message;
import utils.messaging.Messenger;

/**
 * This class is a singleton and handles all inputs that occurs during runtime.
 */
public class MouseInputListener extends MouseInputAdapter{	
	/**
	 * The instance itself.
	 */
	private static MouseInputListener instance;
    
    /**
     * @return An instance of the input manager.
     */
    public static MouseInputListener getInstance() {
         if(instance == null) instance = new MouseInputListener();
         return instance;
    }

    /**
     * @param button The button to look for
     * @return The {@see Message} that was found according to the mouse button that was clicked.
     */
    public static Message getMessageFromMouseButton(int button){
    	switch(button){
    	case MouseEvent.BUTTON1: return Message.MOUSE_LEFT_CLICKED;
    	case MouseEvent.BUTTON2: return Message.MOUSE_RIGHT_CLICKED;
    	case MouseEvent.BUTTON3: return Message.MOUSE_MIDDLE_CLICKED;
    	default: return Message.MOUSE_EXTRA_CLICKED;
    	}
    }
    
    /**
     * Convenience method.
     * @param mouseEvent The {@see MouseEvent} the get the button from
     * @return The {@see Message} that was found according to the mouse button that was clicked.
     */
    public static Message getMessageFromMoueButton(MouseEvent mouseEvent){
    	return getMessageFromMouseButton(mouseEvent.getButton());
    }
    
    /**
     * Fires when a mouse button was clicked.
     */
	@Override
	public void mouseClicked(MouseEvent e) {
		Messenger.sendMessage(Message.MOUSE_CLICKED, e);
	}

	/**
	 * Fires when the mouse entered the registered component.
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		Messenger.sendMessage(Message.MOUSE_ENTERED, e);
	}

	/**
	 * Fires when the mouse exited the registered component.
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		Messenger.sendMessage(Message.MOUSE_EXITED, e);
	}

	/**
	 * Fires when a mouse button was pressed.
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		Messenger.sendMessage(Message.MOUSE_BUTTON_PRESSED, e);
		Messenger.sendMessage(getMessageFromMoueButton(e), e);
	}

	/**
	 * Fires when a mouse button was released.
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		Messenger.sendMessage(Message.MOUSE_BUTTON_RELEASED, e);
		Messenger.sendMessage(getMessageFromMoueButton(e), e);
	}

	/**
	 * Fires when the mouse was dragged.
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		Messenger.sendMessage(Message.MOUSE_DRAGGED, e);
	}

	/**
	 * Fires when the mouse was moved.
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		Messenger.sendMessage(Message.MOUSE_MOVED, e);
	}
}