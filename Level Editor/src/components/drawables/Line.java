package components.drawables;
import java.awt.Graphics;

import utils.math.Vector;

import components.Transform;


public class Line extends Drawable{
	/**
	 * The start point.
	 */
	private Vector startPoint;
	/**
	 * The end point.
	 */
	private Vector endPoint;
	
	/**
	 * Default constructor.
	 */
	public Line() {
		startPoint = Vector.getZeroVector(3);
		endPoint = Vector.getZeroVector(3);
	}
	
	/**
	 * Constructor.
	 * @param startPoint The start point of the line
	 * @param endPoint The end point of the line
	 */
	public Line(Vector startPoint, Vector endPoint){
		this.startPoint = startPoint;
		this.endPoint = endPoint;
	}
	
	/**
	 * @return The start point of the line.
	 */
	public Vector getStartPoint(){
		return this.startPoint;
	}
	
	/**
	 * @return The end point of the line.
	 */
	public Vector getEndPoint(){
		return this.endPoint;
	}
	
	/**
	 * Calculates the center for the specific {@see Drawable}.
	 * @return The {@see IDrawable} itself, for chaining.
	 */
	@Override
	protected IDrawable setCenter(){
		this.center = endPoint.sub(startPoint).scale(.5f);
		return this;
	}
	
	/**
	 * Sets the start point.
	 * @param x1 The x-coordinate
	 * @param y1 The y-coordinate
	 */
	public void setStartPoint(int x1, int y1){
		this.startPoint = new Vector(x1, y1);
	}
	
	/**
	 * Sets the end point.
	 * @param x1 The x-coordinate
	 * @param y1 The y-coordinate
	 */
	public void setEndPoint(int x2, int y2){
		this.endPoint = new Vector(x2, y2);
	}
	
	/**
	 * Renders the {@see IDrawable}.
	 * @param g The {@see Graphics} component that is used for rendering
	 * @return The {@see IDrawable} itself, for chaining.
	 */
	@Override
	public IDrawable render(Graphics g) {
		super.render(g);
		
		Transform transform = getEntity().getComponent(Transform.class);
		Vector position = transform.getPosition();
		Vector startPosition = position.add(startPoint);
		Vector endPosition = position.add(endPoint);
		
		gToUse.drawLine((int)startPosition.get(Vector.X), (int)startPosition.get(Vector.Y), (int)endPosition.get(Vector.X), (int)endPosition.get(Vector.Y));
		return this;
	}
}
