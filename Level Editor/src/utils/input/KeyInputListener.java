package utils.input;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import utils.input.states.KeyStates;
import utils.messaging.Message;
import utils.messaging.Messenger;

/**
 * This class is a singleton and handles all inputs that occurs during runtime.
 */
public class KeyInputListener implements KeyListener{
	/**
	 * The maximal amount of keys of the input device.
	 */
	private static final int MAX_KEYS = 256;
	
	/**
	 * The maximal size of the cache
	 */
	private static final int MAX_CACHE_SIZE = 1024;
	
	/**
	 * The instance itself.
	 */
	private static KeyInputListener instance;
	
	/**
	 * Holds all the key states {@see KeyStates}.
	 */
	private KeyStates[] keyStates;
	
	/**
	 * Holds whether or not any key was pressed.
	 */
	private boolean anyKeyDown;
	
	/**
	 * Holds whether or not any key was released.
	 */
	private boolean anyKeyUp;
	
	/**
	 * The cache that holds the typed characters.
	 */
	private String cache;
	 
	/**
	 * Default constructor.
	 */
    private KeyInputListener() {
    	keyStates = new KeyStates[MAX_KEYS];
    	reset();
    }
    
    /**
     * Sets the key states to their default values.
     */
    private void reset(){
    	for(int i = 0; i < keyStates.length; i++){
    		// Send message
    		if(keyStates[i] != KeyStates.NONE) Messenger.sendMessage(getMessageFromKeyState(keyStates[i]), i);
    		// Reset
    		keyStates[i] = KeyStates.NONE;
    	}
		anyKeyUp = false;
		anyKeyDown = false;
		if(cache == null || cache.length() > MAX_KEYS * MAX_CACHE_SIZE) cache = "";
    }
    
    /**
     * @param keyState The key state to convert into a {@see Message}
     * @return The {@see Message} that was found.
     */
    public Message getMessageFromKeyState(KeyStates keyState){
    	if(keyState == KeyStates.DOWN) return Message.KEY_DOWN;
    	if(keyState == KeyStates.UP) return Message.KEY_UP;
    	return Message.UNHANDLED_KEY_STATE;
    }
    
    /**
     * @return Whether or not any key was pressed.
     */
    public boolean isAnyKeyDown(){
    	return anyKeyDown;
    }
    
    /**
     * @return Whether or not any key was released.
     */
    public boolean isAnyKeyUp(){
    	return anyKeyUp;
    }
    
    /**
     * @param keyCode The key code to look for
     * @return Whether or not the key with the given key code is down.
     */
    public boolean isKeyDown(int keyCode){
    	return keyStates[keyCode].equals(KeyStates.DOWN);
    }
    
    /**
     * @param keyCode The key code to look for
     * @return Whether or not the key with the given key code is up.
     */
    public boolean isKeyUp(int keyCode){
    	return keyStates[keyCode].equals(KeyStates.UP);
    }

    /**
     * @return An instance of the input manager.
     */
    public static KeyInputListener getInstance() {
         if(instance == null) instance = new KeyInputListener();
         return instance;
    }
    
    /**
     * @param keyCode The key code to check
     * @return Whether or not the given key code is valid.
     */
    public boolean isValidKeyCode(int keyCode){
    	return keyCode >= 0 && keyCode < keyStates.length;
    }
    
    /**
     * @return The cached typed characters.
     */
    public String getCache(){
    	return cache;
    }
    
    /**
     * Resets all required values.
     */
    public void update(){
    	reset();
    }

    /**
     * Sets the key states accordingly.
     */
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if((anyKeyDown = isValidKeyCode(keyCode))) keyStates[keyCode] = KeyStates.DOWN;
	}

	/**
     * Sets the key states accordingly.
     */
	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if((anyKeyUp = isValidKeyCode(keyCode))) keyStates[keyCode] = KeyStates.UP;
	}

	/**
	 * Updates the cache.
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		cache += e.getKeyChar();
	}
}