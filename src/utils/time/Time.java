package utils.time;

public class Time {
	/**
	 * The frames per second.
	 */
	public static final float FPS = 60f;
	
	/**
	 * The delta time.
	 */
	public static float deltaTime;
	
	/**
	 * The frame time.
	 */
	public static float frameTime;
	
	/**
	 * The fixed delta time.
	 */
	public static float fixedDeltaTime = 1 / FPS;

	/**
	 * The fixed elapsed time.
	 */
	public static float fixedTime;
	
	/**
	 * The variable elapsed time.
	 */
	public static float variableTime;
	
	/**
	 * The current time.
	 */
	private static float currentTime = System.currentTimeMillis() / 1000;
	
	/**
	 * A buffer for the current time.
	 */
	private static float newTime;
	
	/**
	 * Updates the time.
	 */
	public static void update(){
	    // Calculate variable delta time
	    newTime = System.currentTimeMillis() / 1000;
	    frameTime = newTime - currentTime;
	    currentTime = newTime;
	    
	    while (deltaTime > 0.0 ){	// Stick to ideal frame rate
	        deltaTime = Math.min(frameTime, fixedDeltaTime);
	        frameTime -= deltaTime;
			variableTime += deltaTime;
	    }
		
	    // Update times
		fixedTime += fixedDeltaTime;
	}
}
