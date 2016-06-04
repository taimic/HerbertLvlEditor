package components.drawables;
import java.awt.Graphics;

import utils.math.Vector;

import components.Transform;


public class Polygon extends Drawable{
	/**
	 * All points of the polygon.
	 */
	private Vector[] points;
	/**
	 * The x-coordinates for actually rendering.
	 */
	private int[] xCoordinates;
	/**
	 * The y-coordinates for actually rendering.
	 */
	private int[] yCoordinates;
	
	/**
	 * Default constructor.
	 */
	public Polygon() {
		points = new Vector[0];
	}
	
	/**
	 * Constructor.
	 * @param points The points of the polygon
	 */
	public Polygon(Vector... points) {
		this.points = points;
		this.setCenter();
	}
	
	/**
	 * Calculates the center for the specific {@see Drawable}.
	 * @return The {@see IDrawable} itself, for chaining.
	 */
	@Override
	protected IDrawable setCenter(){
	    this.center = Vector.getZeroVector(points[0].getDimension());
	    
	    for(Vector point : points){
	    	this.center = this.center.add(point);
	    }

	    this.center = this.center.scale(1 / points.length);
		return this;
	}

	/**
	 * Renders the {@see IDrawable}.
	 * @param g The {@see Graphics} component that is used for rendering
	 * @return The {@see IDrawable} itself, for chaining.
	 */
	@Override
	public IDrawable render(Graphics g) {
		super.render(g);
		
		convertCoordinatesToArray();
		
		if(filled) g.fillPolygon(xCoordinates, yCoordinates, xCoordinates.length);
		else g.drawPolygon(xCoordinates, yCoordinates, xCoordinates.length);
		
		return this;
	}
	
	/**
	 * Converts the coordinates to an array, so that the coordinates can be used for rendering.
	 */
	private void convertCoordinatesToArray(){
		Vector bufferPoint;
		xCoordinates = new int[points.length];
		yCoordinates = new int[points.length];
		
		for(int i = 0; i < points.length; i++){
			bufferPoint = points[i].add(this.getEntity().getComponent(Transform.class).getPosition());
			xCoordinates[i] = ((int) bufferPoint.get(Vector.X)); 
			yCoordinates[i] = ((int) bufferPoint.get(Vector.Y)); 
		}
	}
}
