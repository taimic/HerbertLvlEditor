package components;

import utils.math.Vector;

public class CircleCollider extends Collider {
	// The smoothness of the circle
	private static final int SHAPE_SMOOTHNESS = 24;
	
	// The radius
	private float r;
	
	// Default constructor
	public CircleCollider() {
		super();
	}

	// Default constructor
	public CircleCollider(float r, Transform transform) {
		super(transform);
		setRadius(r);
	}

	// Sets the radius
	public CircleCollider setRadius(float r){
		this.r = r;
		return this;
	}
	
	// Returns the current radius
	public float getRadius() {
		return (float) (r * transform.getScale().get(Vector.X));
	}

	@Override
	public boolean isHit(Vector position) {
		return ((float) transform.getPosition().distanceTo(position) <= r  * transform.getScale().get(Vector.X));
	}

	@Override
	public boolean collidesWith(CircleCollider collider) {
		return (float) transform.getPosition().distanceTo(collider.getTransform().getPosition()) < this.r + collider.r;
	}

	@Override
	public boolean collidesWith(BoxCollider boxCollider){       
        float radius = getRadius();
        float angle = (float) boxCollider.getTransform().getRotation().get(Vector.Z);
       
        float boxCenterX = (float) boxCollider.transform.getPosition().get(Vector.X);
        float boxCenterY = (float) boxCollider.transform.getPosition().get(Vector.Y);
                       
        // Rotate circle's center point back
        angle = -(float) Math.toRadians(angle);
        float dx =  (float) ((transform.getPosition().get(Vector.X)) - boxCenterX);
        float dy =  (float) ((transform.getPosition().get(Vector.Y)) - boxCenterY);
       
        float xC = boxCenterX + dx * (float)Math.cos(angle) - dy * (float)Math.sin(angle);
        float yC = boxCenterY + dx * (float)Math.sin(angle) + dy * (float)Math.cos(angle);
         
        // Closest point in the rectangle to the center of circle rotated backwards(unrotated)
        float cdx = (float)Math.abs(xC - boxCenterX);
	    float cdy = (float)Math.abs(yC - boxCenterY);

	    if (cdx > (boxCollider.halfWidth + radius)) return false;
	    if (cdy > (boxCollider.halfHeight + radius)) return false;

	    if (cdx <= (boxCollider.halfWidth)) return true;
	    if (cdy <= (boxCollider.halfHeight)) return true;
	
	    float distance = ((cdx - boxCollider.halfWidth) * (cdx - boxCollider.halfWidth)) + ((cdy - boxCollider.halfHeight) * (cdy - boxCollider.halfHeight));

	    return (distance <= (radius * radius));
	}
	
	@Override
	public boolean collidesWith(Collider collider){
		return collider.collidesWith(this);
	}
	
	/******************************
	 *	ShapeRenderSystem Methods *
	 ******************************/
	
//	@Override
//	public CircleCollider render(ShapeRenderer renderer) {
//		renderer.circle(transform.getPosition().get(Vector.X), transform.getPosition().get(Vector.Y), r * transform.getScale().get(Vector.X), SHAPE_SMOOTHNESS);
//		return this;
//	}
}
