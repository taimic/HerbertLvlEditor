package camera;
import java.awt.Color;
import java.awt.geom.AffineTransform;

import utils.math.Vector;
import camera.viewports.Viewport;

import components.Transform;

public class Camera{	
	/**
	 * The transform of the camera.
	 */
	protected Transform transform;
	
	/**
	 * The viewport of the camera.
	 */
	protected Viewport viewport;
	
	/**
	 * The affine transformation of the camera.
	 */
	protected AffineTransform affineTransform;
	
	/**
	 * Holds whether or no the camera needs an update.
	 */
	protected boolean needsUpdate = true;
	
	/**
	 * The color that is used for clearing.
	 */
	protected Color clearColor = Color.BLACK;
	
	/**
	 * The name of the camera.
	 */
	protected String name;
	
	/**
	 * Default constructor.
	 */
	public Camera() {
		viewport = new Viewport(0, 0);
		affineTransform = new AffineTransform();
		transform = new Transform();
	}
	
	/**
	 * Constructor.
	 * @param viewport The viewport of the camera
	 */
	public Camera(String name, Viewport viewport) {
		this();
		setViewport(viewport);
		this.name = name;
	}
	
	/**
	 * @return The {@see AffineTransform} object.
	 */
	public AffineTransform getAffineTransform(){
		return this.affineTransform;
	}
	
	/**
	 * Sets the name of the camera.
	 * @param name The name
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * @return The name of the camera.
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Sets the size of the {@see Camera} and automatically resizes it.
	 * @param width The new width
	 * @param height The new height
	 */
	public void setSize(int width, int height){
		this.resize(width, height);
	}
	
	/**
	 * Resizes the camera's viewport.
	 * 
	 * @param width The width of the screen
	 * @param height The height of the screen
	 */
	public void resize(float width, float height){
		this.viewport.setSize((int)width, (int)height);
		this.needsUpdate = true;
	}
	
	/**
	 * @return Whether or not the camera needs an update.
	 */
	public boolean needsUpdate(){
		return this.needsUpdate;
	}
	
	/**
	 * @return The camera's transform.
	 */
	public Transform getTransform(){
		return transform;
	}
	
	/**
	 * @return The clear color.
	 */
	public Color getClearColor(){
		return clearColor;
	}
	
	/**
	 * Sets the clear color of the camera to the given one.
	 * @param clearColor The clear color to set
	 * @return The camera itself, for chaining.
	 */
	public Camera setClearColor(Color clearColor){
		this.clearColor = clearColor;
		return this;
	}
	
	/**
	 * @return The camera's viewport.
	 */
	public Viewport getViewport(){
		return viewport;
	}

	/**
	 * @return The width of the viewport.
	 */
	public float getWidth(){
		return this.viewport.getWidth();
	}
	
	/**
	 * @return The height of the viewport.
	 */
	public float getHeight(){
		return this.viewport.getHeight();
	}

	/**
	 * @return The half width of the viewport.
	 */
	public float getHalfWidth(){
		return this.viewport.getHalfWdith();
	}
	
	/**
	 * @return The half height of the viewport.
	 */
	public float getHalfHeight(){
		return this.viewport.getHalfHeight();
	}
	
	/**
	 * Sets the camera's viewport to the given one.
	 * @param viewport The viewport to set
	 * @return The camera itself, for chaining.
	 */
	public Camera setViewport(Viewport viewport){
		this.viewport = viewport;
		return this;
	}
	
	/**
	 * Updates the camera.
	 * @param dt The delta time
	 */
	public Camera update(float dt){
		if(!needsUpdate) return this;
		affineTransform.setToIdentity();
		affineTransform.translate(viewport.getHalfWdith() + transform.getPosition().get(Vector.X), viewport.getHalfHeight() + transform.getPosition().get(Vector.Y));
		needsUpdate = false;
		return this;
	}
	
	/**
	 * @return The x-coordinate of the camera.
	 */
	public double getX(){
		return affineTransform.getTranslateX();
	}
	
	/**
	 * @return The y-coordinate of the camera.
	 */
	public double getY(){
		return affineTransform.getTranslateY();
	}
	
	public Vector screenToWorldCoordinates(Vector screenCoordinates){
		
		// TODO: map screen coords to world coords
		return screenCoordinates.copy();
	}
	
	public Vector worldToScreenCoordinates(Vector worldCoordinates){

		// TODO: map world coords to screen coords
		return worldCoordinates.copy();
	}
	
//	function point2D get2dPoint(Point3D point3D, Matrix viewMatrix,
//            Matrix projectionMatrix, int width, int height) {
//
// Matrix4 viewProjectionMatrix = projectionMatrix * viewMatrix;
// //transform world to clipping coordinates
// point3D = viewProjectionMatrix.multiply(point3D);
// int winX = (int) Math.round((( point3D.getX() + 1 ) / 2.0) *
//                              width );
// //we calculate -point3D.getY() because the screen Y axis is
// //oriented top->down 
// int winY = (int) Math.round((( 1 - point3D.getY() ) / 2.0) *
//                              height );
// return new Point2D(winX, winY);
//}
	
//	function Point3D get3dPoint(Point2D point2D, int width,
//	        int height, Matrix viewMatrix, Matrix projectionMatrix) {
//	 
//	        double x = 2.0 * winX / clientWidth - 1;
//	        double y = - 2.0 * winY / clientHeight + 1;
//	        Matrix4 viewProjectionInverse = inverse(projectionMatrix *
//	             viewMatrix);
//
//	        Point3D point3D = new Point3D(x, y, 0); 
//	        return viewProjectionInverse.multiply(point3D);
//	}
}
