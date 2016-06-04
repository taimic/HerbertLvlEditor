package services.systems;
import java.util.HashMap;
import java.util.Map;

import services.ServiceSystem;
import utils.messaging.IMessageListener;
import utils.messaging.Message;
import utils.messaging.Messenger;
import camera.Camera;
import camera.viewports.Viewport;

public class CameraSystem extends ServiceSystem<Camera> implements IMessageListener{
	/**
	 * The name of the main camera (which is the default camera, no matter if there was a camera added or not).
	 */
	public static final String MAIN_CAMERA_NAME = "Main Camera";
	
	/**
	 * The cameras.
	 */
	private Map<String, Camera> cameras;
	
	/**
	 * Default constructor.
	 */
	public CameraSystem() {
		cameras = new HashMap<>();
		addCamera(new Camera(MAIN_CAMERA_NAME, new Viewport(0, 0)));
		Messenger.register(this, Message.RESIZE);
	}
	
	/**
	 * @param name The name of the camera
	 * @return The camera that was found, NULL otherwise.
	 */
	public Camera getCamera(String name){
		if(hasCamera(name)) return cameras.get(name);
		return null;
	}
	
	/**
	 * Adds the given camera, if not added yet.
	 * 
	 * @param name The name of the camera
	 * @param camera The camera to add
	 * @return The CameraSystem itself, for chaining.
	 */
	public CameraSystem addCamera(Camera camera){
		if(hasCamera(camera.getName())) return this;
		this.cameras.put(camera.getName(), camera);
		return this;
	}
	
	/**
	 * @param name The name of the camera
	 * @return Whether or not the camera was added to the system.
	 */
	public boolean hasCamera(String name){
		return this.cameras.containsKey(name);
	}
	
	/**
	 * Updates all cameras.
	 * @param dt The delta time
	 * @return The CameraSystem itself, for chaining.
	 */
	public CameraSystem update(float dt){
		for(Camera camera : cameras.values()){
			camera.update(dt);
		}
		return this;
	}
	
	public CameraSystem resize(float width, float height){
		for(Camera camera : cameras.values()){
			camera.resize(width, height);
		}
		return this;
	}

	@Override
	public void update() {
		// TODO: is this necessary?
	}

	/**
	 * Resizes the camera, if a corresponding {@see Message} was sent.
	 */
	@Override
	public void onMessage(Message message, Object... data) {
		if(Messenger.is(Message.RESIZE, message)) this.resize((float) data[0], (float) data[1]);
	}
}
