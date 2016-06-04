import java.util.List;

import persistance.XMLComposer;
import scenes.Scene;
import services.ServiceLocator;
import services.systems.CameraSystem;
import services.systems.CollisionSystem;
import services.systems.EditorSystem;
import services.systems.EntitySystem;
import services.systems.SceneSystem;
import utils.input.KeyInputListener;
import utils.input.MouseInputListener;
import utils.input.mouse.Mouse;
import utils.input.mouse.MouseAppearance;
import utils.messaging.IMessageListener;
import utils.messaging.Message;
import utils.messaging.Messenger;
import utils.time.Time;

// TODO: Config file for various settings of the level editor
// TODO: asset manager
public class main {
	/**
	 * The width of the application.
	 */
	private static final int SCREEN_WIDTH = 800;
	/**
	 * The height of the application.
	 */
	private static final int SCREEN_HEIGHT = 600;
	
	/**
	 * The level editor.
	 */
	static LevelEditor levelEditor;
	/**
	 * The main loop.
	 */
	static MainLoop mainLoop;
	
	/**
	 * The main entrance point.
	 * @param args All command line arguments
	 */
	public static void main(String[] args){
		// Create level editor
		levelEditor = new LevelEditor(SCREEN_WIDTH, SCREEN_HEIGHT);
		levelEditor.addKeyListener(KeyInputListener.getInstance());
		
		// Mouse look and feel
		Mouse.getInstance();
		MouseAppearance.setWindow(levelEditor);
		
		// Add services to service locator
		ServiceLocator.addService(new SceneSystem().activate());
		ServiceLocator.addService(new CameraSystem().resize(levelEditor.getWidth(), levelEditor.getHeight()).activate());
		ServiceLocator.addService(new EntitySystem().activate());
		ServiceLocator.addService(new EditorSystem().activate());
		ServiceLocator.addService(new CollisionSystem().activate());
		
		// TODO: read file name from configuration file
		List<Scene> scenes = new XMLComposer<Scene>("config/scenes.xml", Scene.class).compose().getData();
		for(Scene scene : scenes){
			scene.addMouseListener(MouseInputListener.getInstance());
			scene.addMouseMotionListener(MouseInputListener.getInstance());
			levelEditor.add(scene);
		}

		// Resize the level editor, so every scene with a default camera has the correct dimensions
		levelEditor.resize();
		
		// Create main loop
		mainLoop = new MainLoop();
		mainLoop.start();
	}
}

/**
 * The main loop.
 */
class MainLoop implements Runnable, IMessageListener{
	/**
	 * The message that will be printed when the main loop was started.
	 */
	public static final String MAIN_LOOP_STARTED = "Main loop started.";
	/**
	 * The message that will be printed when the main loop was stopped.
	 */
	public static final String MAIN_LOOP_STOPPED = "Main loop stopped.";
	
	/**
	 * Holds whether or no the application is running.
	 */
	private volatile boolean running;
	/**
	 * The current thread.
	 */
	private Thread thread = new Thread(this);
	
	/**
	 * Default constructor.
	 */
	public MainLoop() {
		Messenger.register(this, Message.QUIT);
	}
	
	/**
	 * Starts the main loop.
	 */
	public void start(){
		System.out.println(MAIN_LOOP_STARTED);
		this.running = true;
		thread.start();
	}
	
	/**
	 * Stops the main loop.
	 */
	public void stop(){
		this.running = false;
		System.out.println(MAIN_LOOP_STOPPED);
	}

	/**
	 * The code that shall be executed during run time.
	 */
	@Override
	public void run() {
		while(running) {
			Messenger.update();
			Time.update();
			ServiceLocator.getService(CameraSystem.class).update(Time.deltaTime);
			ServiceLocator.update();
			KeyInputListener.getInstance().update();
		}
		dispose();
	}
	
	/**
	 * @return Whether or not the main loop is running.
	 */
	public boolean isRunning(){
		return this.running;
	}
	
	/**
	 * Disposes everything that needs to be disposed.
	 */
	public void dispose(){
		// Check for valid thread
		if(thread == null) return;
		
		// Try to terminate the thread
		try {
			this.thread.join();
		} catch (InterruptedException e) {
			System.err.println("Could not properly terminate thread.");
			e.printStackTrace();
		}finally{
			this.thread = null;			
		}
	}

	/**
	 * Handles all messages.
	 */
	@Override
	public void onMessage(Message message, Object... data) {
		if(Messenger.is(Message.QUIT, message)) this.stop();
	}
}
