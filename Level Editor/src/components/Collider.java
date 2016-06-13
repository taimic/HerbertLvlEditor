package components;

import java.util.BitSet;

import services.ServiceLocator;
import services.systems.CollisionSystem;
import utils.math.Vector;
import entity.Entity;

public abstract class Collider extends Component{
	// The transform holding the respective data
	protected Transform transform;
	// The collision flag
	private BitSet flag;
	// The collision mask
	private BitSet mask;
	
	// Default constructor
	public Collider() {
		
//		this(new Transform());
		ServiceLocator.getService(CollisionSystem.class).add(this);
	}
	
	// Default constructor
	public Collider(Transform transform) {
		setTransform(transform);
		// Register at the ShapeRenderer
		//ServiceLocator.getService(ShapeRenderSystem.class).addCollider(this);
		// Register at the CollisionDetector
		ServiceLocator.getService(CollisionSystem.class).add(this);
	}
	
	// Returns the transform of the collider
	public Transform getTransform(){
		return this.transform;
	}
	
	// Sets the transform
	public Collider setTransform(Transform transform){
		this.transform = transform;
		return this;
	}
	
	// Returns whether or not the given values collide with the collider
	public abstract boolean isHit(Vector position);
	
	// Returns whether or not the given values collide with the collider
	public boolean isHit(float x, float y){
		return isHit(new Vector(x, y, 0));
	}

	// Returns whether or not we collide with the given circle collider
	public abstract boolean collidesWith(CircleCollider collider);
	
	// Returns whether or not we collide with the given box collider
	public abstract boolean collidesWith(BoxCollider collider);
	
	// Returns whether or not we collide with the given collider
	public abstract boolean collidesWith(Collider collider);
	
	@Override
	public Collider update(){
		// Update transform
		setTransform(getEntity().getComponent(Transform.class));
		return this;
	}
	
	// Sets exactly one bit of the flag
	public Collider setFlag(int flag){
		this.flag.set(flag);
		return this;
	}
	
	// Clears exactly one bit of the flag
	public Collider clearFlag(int flag){
		this.flag.clear(flag);
		return this;
	}
	
	// Sets exactly on bit of the mask
	public Collider setMask(int mask){
		this.mask.set(mask);
		return this;
	}
	
	// Clears exactly one bit of the mask
	public void clearMask(int mask){
		this.mask.clear(mask);
	}
	
	// The debug rendering method
//	public abstract Collider render(ShapeRenderer renderer);
	
	@Override
	public Collider setEntity(Entity entity){
		super.setEntity(entity);
		this.setTransform(entity.getComponent(Transform.class));
		return this;
	}
}
