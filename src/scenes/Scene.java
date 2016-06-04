package scenes;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.JComponent;
import javax.swing.JPanel;

import services.ServiceLocator;
import services.systems.CameraSystem;
import services.systems.SceneSystem;
import statemachine.StateMachine;
import statemachine.state.SceneState;
import camera.Camera;

import components.Component;
import components.Resizable;
import components.drawables.Drawable;
import components.drawables.IDrawable;

import entity.Entity;

public class Scene extends JPanel implements IScene{
	/**
	 * The serialization ID.
	 */
	private static final long serialVersionUID = -2419176795343783656L;
	/**
	 * The default scene name.
	 */
	public static final String DEFAULT_SCENE_NAME = "DefaultScene";
	
	/**
	 * The z-index.
	 */
	private static int globalZ;
	
	/**
	 * The z-index of this scene.
	 */
	private int z;
	
	/**
	 * The camera that is used for rendering.
	 */
	protected Camera camera;
	
	/**
	 * The original width of the scene.
	 */
	protected float originalWidth;
	
	/**
	 * The original height of the scene.
	 */
	protected float originalHeight;
	
	/**
	 * The state machine for all scenes.
	 */
	public static StateMachine stateMachine = new StateMachine(SceneState.Mouse_Exited);
	
	/**
	 * All elements to draw.
	 */
	private Queue<IDrawable> drawables;
	
	/**
	 * Default constructor.
	 */
	public Scene() {
		drawables = new ConcurrentLinkedQueue<>();
		z = globalZ;
		globalZ++;
		camera = ServiceLocator.getService(CameraSystem.class).getCamera(CameraSystem.MAIN_CAMERA_NAME);
		this.setSize((int) camera.getWidth(), (int) camera.getHeight());
		this.setName("");
		
		ServiceLocator.getService(SceneSystem.class).add(this);
	}
	
	/**
	 * Constructor.
	 * @param camera The camera that shall be used for rendering
	 */
	public Scene(Camera camera){
		this();
		this.camera = camera;
		this.setSize((int) camera.getWidth(), (int) camera.getHeight());
	}
	
	/**
	 * Constructor.
	 * @param name The name of the scene.
	 */
	public Scene(String name){
		this();
		setName(name);
	}
	
	/**
	 * Sets the name. If no name was provided, a default name is set, using the current instance count.
	 * @param name The name to set.
	 */
	public void setName(String name){
		if(name == null || name.isEmpty()) name = DEFAULT_SCENE_NAME + z;
		super.setName(name);
	}
	
	/**
	 * Sets the camera of the scene.
	 * @param camera The camera to set
	 * @return The scene itself, for chaining.
	 */
	public Scene setCamera(Camera camera){
		this.camera = camera;
		this.setSize((int) camera.getWidth(), (int) camera.getHeight());
		return this;
	}

	/**
	 * @return The z-index of the scene.
	 */
	@Override
	public int getZIndex() {
		return z;
	}

	/**
	 * Sets the z-index of the scene.
	 * @param The z-index
	 */
	@Override
	public void setZIndex(int z) {
		this.z = z;
	}
	
	/**
	 * @return The camera that is in use.
	 */
	public Camera getCamera(){
		return camera;
	}
	
	/**
	 * Adds a new draw-able element to the camera.
	 * @param drawable The draw-able element, see {@see IDrawable}
	 */
	public void addDrawable(IDrawable drawable){
		if(!this.drawables.contains(drawable)) this.drawables.add(drawable);
	}
	
	/**
	 * Adds all {@see IDrawable}s from the given entity to the list.
	 * @param entity The entity to traverse
	 */
	public void addEntity(Entity entity){
		Map<Class<? extends Component>, Component> components = entity.getComponents();
		for(Entry<Class<? extends Component>, Component> entry : components.entrySet()){
			if(entry.getValue() instanceof IDrawable) addDrawable((IDrawable) entry.getValue());
		}
	}
	
	/**
	 * Overrides the {@see JPanel} paint method, so we can customize the painted content.
	 * Use the render method to define what is going to be rendered.
	 */
	@Override
    public void paintComponent(Graphics g) {
        // Default painting behavior
		super.paintComponent(g);
        
        // Clear camera
        super.setBackground(camera.getClearColor());
        super.setOpaque(false);
        
        // Set the camera's transformation matrix (global transformation)
    	Graphics2D gToUse = (Graphics2D) g;
    	gToUse.setTransform(camera.getAffineTransform());
    	
        // Render content
        for(IDrawable drawable : this.drawables) drawable.render(gToUse);
    }
	
	/**
	 * Resizes the camera, if the component gets resized.
	 */
	@Override
	public void setSize(int width, int height){
		this.originalWidth = width;
		this.originalHeight = height;
	}
	
	/**
	 * Adds all {@see Drawable}s within the entities.
	 * @param entities The entities containing the {@see Drawable}s
	 * @return The scene itself, for chaining.
	 */
	public Scene addDrawables(Map<String, Entity> entities){
		for(Entity entity : entities.values()){
			Map<Class<? extends Component>, Component> components = entity.getComponents();
			for(Component component : components.values()){
				if(component instanceof Drawable)
					this.addDrawable((IDrawable) entity.getComponent(component.getClass()));
			}
		}
		return this;
	}
	
	/**
	 * Resizes the scene, keeping the aspect ratio.
	 */
	@Override
	public void resize(float width, float height){
		if(originalWidth == 0 || originalHeight == 0) return;
		this.setBounds(0, 0, (int)(originalWidth * width), (int)(originalHeight * height));
		
		resizeDrawables(width, height);
		
		if (getParent() != null) ((JComponent) getParent()).revalidate();
		render();
	}
	
	public void resizeDrawables(float width, float height){
		Resizable resizable;
		Entity entity;
		for(IDrawable drawable : drawables){
			entity = ((Component)drawable).getEntity();
			if(entity == null) continue;
			if((resizable = entity.getComponent(Resizable.class)) != null) resizable.resize(width, height);
		}
	}

	/**
	 * Re-renders/Updates the system.
	 * 
	 * @return The render system itself, for chaining.
	 */
	@Override
	public void render() {
		repaint();
		stateMachine.update();
	}

	/**
	 * @return The list with all {@see Drawable}s.
	 */
	@Override
	public Queue<IDrawable> getDrawables() {
		return drawables;
	}
}
