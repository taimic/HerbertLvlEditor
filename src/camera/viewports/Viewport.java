package camera.viewports;

public class Viewport {
	/**
	 * The width of the viewport.
	 */
	private float width;
	/**
	 * The height of the viewport.
	 */
	private float height;
	/**
	 * The half width of the viewport.
	 */
	private float halfWdith;
	/**
	 * The half height of the viewport.
	 */
	private float halfHeight; 
	
	/**
	 * Constructor.
	 * @param width The width of the viewport
	 * @param height The height of the viewport
	 */
	public Viewport(float width, float height) {
		setWidth(width);
		setHeight(height);
	}

	/**
	 * @return The width of the viewport.
	 */
	public float getWidth() {
		return width;
	}
	
	/**
	 * Sets the size of the viewport. Convenience method.
	 * @param width The width of the viewport
	 * @param height The height of the viewport
	 * @return The Viewport itself, for chaining.
	 */
	public Viewport setSize(int width, int height){
		this.setWidth(width);
		this.setHeight(height);
		return this;
	}

	/**
	 * Sets the width of the viewport.
	 * @param width The width to set
	 * @return The viewport itself, for chaining.
	 */
	public Viewport setWidth(float width) {
		this.width = width;
		this.halfWdith = width * .5f;
		return this;
	}
	
	/**
	 * @return The ratio of the viewport.
	 */
	public float getRatio(){
		return this.width / this.height;
	}

	/**
	 * @return The height of the viewport.
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * Sets the height of the viewport.
	 * @param height The width to set
	 * @return The viewport itself, for chaining.
	 */
	public Viewport setHeight(float height) {
		this.height = height;
		this.halfHeight = height * .5f;
		return this;
	}

	/**
	 * @return The half width of the viewport.
	 */
	public float getHalfWdith() {
		return halfWdith;
	}

	/**
	 * @return The half height of the viewport.
	 */
	public float getHalfHeight() {
		return halfHeight;
	}
}
