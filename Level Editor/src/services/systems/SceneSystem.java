package services.systems;

import scenes.Scene;
import services.ServiceSystem;
import utils.messaging.IMessageListener;
import utils.messaging.Message;
import utils.messaging.Messenger;

public class SceneSystem extends ServiceSystem<Scene> implements IMessageListener{		
	/**
	 * Default constructor.
	 */
	public SceneSystem() {
		Messenger.register(this, Message.RESIZE);
	}
	
	/**
	 * Resizes all {@see Scene}s, considering the aspect ratio.
	 * @param width The new width
	 * @param height The new height
	 */
	public void resize(float width, float height){
		for(Scene scene : this.objects){
			scene.resize(width, height);
		}
	}
	
	/********************
	 * Service Methods	*
	 ********************/
	
	/**
	 * Renders all the {@see Scene}s.
	 */
	@Override
	public void update() {
		for (Scene scene : this.objects) {
			scene.render();
		}
	}

	/**
	 * Resizes the scenes, if a corresponding {@see Message} was sent.
	 */
	@Override
	public void onMessage(Message message, Object... data) {
		if(Messenger.is(Message.RESIZE, message)) this.resize((float) data[0], (float) data[1]);
	}
}
