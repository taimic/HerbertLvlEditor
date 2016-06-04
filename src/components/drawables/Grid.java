package components.drawables;

import java.awt.Color;
import java.awt.Graphics;

import utils.math.Vector;

public class Grid extends Drawable{
	/**
	 * The serialization ID.
	 */
	private static final long serialVersionUID = -2834619611013670611L;
	
	/**
	 * The horizontal line count.
	 */
	private int horizontalLineCount;
	/**
	 * The vertical line count.
	 */
	private int verticalLineCount;
	/**
	 * The width of the screen.
	 */
	private int width;
	/**
	 * The height of the screen.
	 */
	private int height;
	
	/**
	 * Default constructor.
	 */
	public Grid() {
		this.setColor(Color.LIGHT_GRAY);
	}
	
	/**
	 * Constructor.
	 * @param horizontalLineCount The horizontal line count
	 * @param verticalLineCount The vertical line count
	 */
	public Grid(int horizontalLineCount, int verticalLineCount) {
		this();
		this.horizontalLineCount = horizontalLineCount;
		this.verticalLineCount = verticalLineCount;
		this.setCenter();
	}
	
	/**
	 * Constructor.
	 * @param horizontalLineCount The horizontal line count
	 * @param verticalLineCount The vertical line count
	 * @param color The color of the lines
	 */
	public Grid(int horizontalLineCount, int verticalLineCount, Color color) {
		this(horizontalLineCount, verticalLineCount);
		this.color = color;
	}
	
	/**
	 * Sets the vertical line count.
	 * @param verticalLineCount The vertical line count
	 * @return The {@see IDrawable}, for chaining.
	 */
	public IDrawable setVerticalLineCount(int verticalLineCount){
		this.verticalLineCount = verticalLineCount;
		return this;
	}
	
	/**
	 * Sets the horizontal line count.
	 * @param verticalLineCount The horizontal line count
	 * @return The {@see IDrawable}, for chaining.
	 */
	public IDrawable setHorizontalLineCount(int horizontalLineCount){
		this.horizontalLineCount = horizontalLineCount;
		return this;
	}
	
	/**
	 * Sets the center of the {@see Drawable}.
	 */
	@Override
	protected IDrawable setCenter(){
		this.center = new Vector(this.width * .5f, this.height * .5f);
		return this;
	}
	
	/**
	 * Sets the width. Also updates the center.
	 * @param width The width
	 */
	public void setWidth(int width){
		this.width = width;
		setCenter();
	}
	
	/**
	 * Sets the height. Also updates the center.
	 * @param height The height
	 */
	public void setHeight(int height){
		this.height = height;
		setCenter();
	}
	
	/**
	 * @return The height.
	 */
	public int getHeight(){
		return this.height;
	}
	
	/**
	 * @return The width.
	 */
	public int getWidth(){
		return this.width;
	}
	
	/**
	 * Renders the {@see Drawable}.
	 */
	@Override
	public IDrawable render(Graphics g) {
		super.render(g);
		
		// Horizontal lines
		int offsetY = height / horizontalLineCount;
		for(int i = 0; i <= horizontalLineCount; i++){
			g.drawLine(0, i * offsetY, width, i * offsetY);
		}
		
		// Vertical lines
		int offsetX = width / verticalLineCount;
		for(int i = 0; i <= verticalLineCount; i++){
			g.drawLine(i * offsetX, 0, i * offsetX, height);
		}
		return this;
	}
	
	/**
	 * Resizes the grid.
	 * @param width The new width
	 * @param height The new height
	 */
	@Override
	public Grid resize(float width, float height){
		this.setWidth((int) (this.originalWidth * width));
		this.setHeight((int) (this.originalHeight * height));
		return this;
	}
}
