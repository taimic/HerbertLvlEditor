package scenes;

import java.util.Map;
import java.util.Queue;

import camera.Camera;

import components.drawables.IDrawable;

import entity.Entity;

public interface IScene {
	/**
	 * @return The z-index that was set.
	 */
	int getZIndex();
	
	/**
	 * Sets the z-index.
	 * @param z The z-index
	 */
	void setZIndex(int z);
	
	/**
	 * Renders the scene.
	 */
	void render();
	
	/**
	 * Sets the camera of the scene.
	 * @param camera The camera to set
	 * @return The scene itself, for chaining.
	 */
	IScene setCamera(Camera camera);
	
	/**
	 * Adds all {@see Drawable}s within the entities.
	 * @param entities The entities containing the {@see Drawable}s
	 * @return The scene itself, for chaining.
	 */
	IScene addDrawables(Map<String, Entity> entities);
	
	/**
	 * @return The name of the scene.
	 */
	String getName();
	
	/**
	 * Sets the name of the scene.
	 * @param name The name to set
	 * @return The scene itself, for chaining.
	 */
	void setName(String name);
	
	/**
	 * @return The list containing all {@see Drawable}s.
	 */
	Queue<IDrawable> getDrawables();

	void resize(float width, float height);
}
