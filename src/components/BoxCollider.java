package components;

import utils.math.Vector;
import utils.messaging.IMessageListener;
import utils.messaging.Message;
import utils.messaging.Messenger;

public class BoxCollider extends Collider implements IMessageListener{
	// The width of the collider
	protected float width;
	// The height of the collider
	protected float height;
	// The half width
	protected float halfWidth;
	// The half height
	protected float halfHeight;
	
	// Default constructor
	public BoxCollider() {
		super();
		Messenger.register(this, Message.RESIZE);
	}
	
	// Default constructor
	public BoxCollider(float width, float height, Transform transform) {
		super(transform);
		setWidth(width);
		setHeight(height);
	}
	
	// Sets the width
	public BoxCollider setWidth(float width){
		this.width = width;
		this.originalWidth = width;
		this.halfWidth = width * .5f;
		return this;
	}
	
	// Set the height
	public BoxCollider setHeight(float height){
		this.height = height;
		this.originalHeight = height;
		this.halfHeight = height * .5f;
		return this;
	}
	
	// Returns the width
	public float getWidth(){
		return width;
	}
	
	// Returns the height
	public float getHeight(){
		return height;
	}
	
	transient Vector rotatedPoint = Vector.getZeroVector(2);
	 
	@Override
	public boolean isHit(Vector position) {
		float angle = -(float) Math.toRadians(getTransform().getRotation().get(Vector.Z));
        rotatedPoint = getRelativeRotatedPoint((float) transform.getPosition().get(Vector.X), (float) transform.getPosition().get(Vector.Y), (float) position.get(Vector.X), (float) position.get(Vector.Y), angle);
		
		return (rotatedPoint.get(Vector.X) >= getLeftExtent() && rotatedPoint.get(Vector.X) <= getRightExtent())
				&& (rotatedPoint.get(Vector.Y) <= getBottomExtent() && rotatedPoint.get(Vector.Y) >= getTopExtent() );
	}
	
	// Calculates the rotated point according to the given angle (in radians)
	private Vector getRotatedPoint(float x, float y, float angle){
		float rotatedX = x * (float)Math.cos(angle) - y * (float)Math.sin(angle);
        float rotatedY = x * (float)Math.sin(angle) + y * (float)Math.cos(angle);
		
		return new Vector(rotatedX, rotatedY);
	}
	
	// Calculates the rotated point according to the given angle (in radians), relative to the given values x1 and y1
	private Vector getRelativeRotatedPoint(float x1, float y1, float x2, float y2, float angle){
		x2 -= x1;
		y2 -= y1;
		
		return getRotatedPoint(x2, y2, angle).add(x1, y1);
	}

	@Override
	public boolean collidesWith(CircleCollider circleCollider) {
		float radius = circleCollider.getRadius();
        float angle = (float) getTransform().getRotation().get(Vector.Z);
       
        float boxCenterX = (float) transform.getPosition().get(Vector.X);
        float boxCenterY = (float) transform.getPosition().get(Vector.Y);
                       
        // Rotate circle's center point back
        angle = -(float) Math.toRadians(angle);
        float dx =  (float) ((circleCollider.transform.getPosition().get(Vector.X)) - boxCenterX);
        float dy =  (float) ((circleCollider.transform.getPosition().get(Vector.Y)) - boxCenterY);
        
        Vector rotatedCenter = getRelativeRotatedPoint(boxCenterX, boxCenterY, dx, dy, angle);
  
        // Closest point in the rectangle to the center of circle rotated backwards(unrotated)
        float cdx = (float)Math.abs(rotatedCenter.get(Vector.X) - boxCenterX);
	    float cdy = (float)Math.abs(rotatedCenter.get(Vector.Y) - boxCenterY);

	    if (cdx > (halfWidth + radius)) return false;
	    if (cdy > (halfHeight + radius)) return false;

	    if (cdx <= (halfWidth)) return true;
	    if (cdy <= (halfHeight)) return true;
	
	    float distance = ((cdx - halfWidth) * (cdx - halfWidth)) + ((cdy - halfHeight) * (cdy - halfHeight));

	    return (distance <= (radius * radius));
	}

	@Override
	public boolean collidesWith(BoxCollider collider) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public float getLeftExtent(){
		return (float) (transform.getPosition().get(Vector.X));// - halfWidth * transform.getScale().get(Vector.X));
	}
	
	public float getRightExtent(){
		return (float) (transform.getPosition().get(Vector.X) + width * transform.getScale().get(Vector.X));// + halfWidth * transform.getScale().get(Vector.X));
	}
	
	public float getTopExtent(){
		return (float) (transform.getPosition().get(Vector.Y));// + halfHeight * transform.getScale().get(Vector.Y));
	}
	
	public float getBottomExtent(){
		return (float) (transform.getPosition().get(Vector.Y) + height * transform.getScale().get(Vector.Y));// - halfHeight * transform.getScale().get(Vector.Y));
	}
	
	@Override
	public boolean collidesWith(Collider collider){
		return collider.collidesWith(this);
	}

	@Override
	public void onMessage(Message message, Object... data) {
		if(Messenger.is(Message.RESIZE, message)) {
			this.width = this.originalWidth * (float)data[0];
			this.height = this.originalHeight * (float)data[1];
		}
	}
}
