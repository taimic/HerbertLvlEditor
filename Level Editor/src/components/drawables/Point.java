package components.drawables;
import java.awt.Graphics;

import utils.math.Vector;

public class Point extends Drawable{
	/**
	 * The coordinates of the point.
	 */
	private Vector coordinates;
	
	/**
	 * Default constructor.
	 */
	public Point() {
		this.coordinates = Vector.getZeroVector(3);
	}
	
	/**
	 * Constructor.
	 * @param coordinates The coordinates of the point
	 */
	public Point(Vector coordinates) {
		this.coordinates = coordinates;
	}
	
	/**
	 * Sets the coordinates of the point.
	 * @param x The x-coordinate
	 * @param y The y-coordinate
	 */
	public void setCoordinates(int x, int y){
		this.coordinates = new Vector(x, y);
		this.setCenter();
	}
	
	/**
	 * Calculates the center for the specific {@see Drawable}.
	 * @return The {@see IDrawable} itself, for chaining.
	 */
	@Override
	protected IDrawable setCenter(){
		this.center = new Vector();
		return this;
	}
	
	/**
	 * @return The coordinates of the point.
	 */
	public Vector getCoordinates(){
		return this.coordinates;
	}

	@Override
	public IDrawable render(Graphics g) {
		super.render(g);
		
		int x = (int)this.coordinates.get(Vector.X),
			y = (int)this.coordinates.get(Vector.Y);
		
		g.drawLine(x, y, x, y);
		
		return this;
	}
}
