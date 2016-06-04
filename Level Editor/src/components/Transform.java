package components;

import utils.math.Vector;


public class Transform extends Component{
	/**
	 * The serialization ID.
	 */
	private static final long serialVersionUID = 5074302378281422331L;
	
	/**
	 * The position of the entity.
	 */
	private Vector position = Vector.getZeroVector(3);
	/**
	 * The rotation of the entity.
	 */
	private Vector rotation = Vector.getZeroVector(3);
	/**
	 * The scale of the entity.
	 */
	private Vector scale = new Vector(1, 1, 1);
	
	/**
	 * Default constructor.
	 */
	public Transform() {
		super();
	}
	
	/**
	 * @return The position of the entity.
	 */
	public Vector getPosition() {
		return position;
	}
	
	/**
	 * Sets the position of the entity.
	 * @param position The new position
	 * @return The {@see Transform} itself, for chaining.
	 */
	public Transform setPosition(Vector position) {
		this.position = position;
		return this;
	}
	
	/**
	 * @return The rotation of the entity.
	 */
	public Vector getRotation() {
		return rotation;
	}
	
	/**
	 * Sets the rotation of the entity.
	 * @param rotation The new rotation
	 * @return The {@see Transform} itself, for chaining.
	 */
	public Transform setRotation(Vector rotation) {
		this.rotation = rotation;
		return this;
	}
	
	/**
	 * @return The scale of the entity.
	 */
	public Vector getScale() {
		return scale;
	}
	
	/**
	 * Sets the scale of the entity.
	 * @param scale The new scale
	 * @return The {@see Transform} itself, for chaining.
	 */
	public Transform setScale(Vector scale) {
		this.scale = scale;
		return this;
	}
	
	/**
	 * Sets the position of the entity.
	 * @param coordinates The coordinates
	 * @return The {@see Transform} itself, for chaining.
	 */
	public Transform setPosition(double... coordinates){
		this.position = new Vector(coordinates);
		return this;
	}
	
	/**
	 * Sets the rotation of the entity.
	 * @param angles The angles for each axis
	 * @return The {@see Transform} itself, for chaining.
	 */
	public Transform setRotation(double... angles){
		this.rotation = new Vector(angles);
		return this;
	}
	
	/**
	 * Sets the scale of the entity.
	 * @param scales The scales for each axis
	 * @return The {@see Transform} itself, for chaining.
	 */
	public Transform setScale(double... scales){
		this.scale = new Vector(scales);
		return this;
	}
	
	/**
	 * Resizes the position.
	 */
	@Override
	public Component resize(float widthRatio, float heightRatio){
		this.position = this.position.multiplyComponentwise(new Vector(widthRatio, heightRatio, 1));
		return this;
	}
}
