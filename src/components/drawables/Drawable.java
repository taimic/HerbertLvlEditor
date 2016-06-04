package components.drawables;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;

import scenes.Scene;
import services.ServiceLocator;
import services.systems.SceneSystem;
import utils.math.Vector;

import components.Component;
import components.Transform;


public abstract class Drawable extends Component implements IDrawable{
	/**
	 * The serialization ID.
	 */
	private static final long serialVersionUID = -2079847776711499769L;
	
	/**
	 * The stroke that is used to render this {@see IDrawable}.
	 */
	protected BasicStroke stroke;
	/**
	 * The {@see Graphics} object that is used to render this {@see IDrawable}.
	 */
	protected transient Graphics2D gToUse;
	/**
	 * The color.
	 */
	protected Color color = Color.WHITE;
	/**
	 * Holds whether or not the {@see IDrawable} is filled.
	 */
	protected boolean filled;
	/**
	 * The alpha value for transparency.
	 */
	protected float alpha = 1.0f;
	/**
	 * The alpha mode that is used for blending.
	 */
	protected int alphaMode = AlphaComposite.SRC_OVER;
	/**
	 * The {@see AffineTransform} that is used for transforming.
	 */
	protected AffineTransform transform = new AffineTransform();
	/**
	 * The center point.
	 */
	protected Vector center;
	/**
	 * The {@see Scene} of this {@see Drawable}.
	 */
	protected transient Scene scene;
	
	/**
	 * Default constructor.
	 */
	public Drawable() {
		scene = ServiceLocator.getService(SceneSystem.class).getLastAddedObject();
		scene.addDrawable(this);
	}
	
	/**
	 * Calculates the center for the specific {@see Drawable}.
	 * @return The {@see IDrawable} itself, for chaining.
	 */
	protected abstract IDrawable setCenter();
	
	/**
	 * @return The calculated center point. Since a {@see Point} 
	 * is a {@see IDrawable} this should not be used for chaining.
	 */
	public Vector getCenter(){
		return this.center;
	}
	
	/**
	 * @return The {@see Scene} of this {@see Drawable}.
	 */
	public Scene getScene(){
		return this.scene;
	}

	/**
	 * Renders the {@see IDrawable}.
	 * @param g The {@see Graphics} component that is used for rendering
	 * @return The {@see IDrawable} itself, for chaining.
	 */
	@Override
	public IDrawable render(Graphics g) {
		gToUse = (Graphics2D) g;
		
		// Set stroke
		if(stroke != null){
			gToUse = (Graphics2D) g.create();
			gToUse.setStroke(stroke);
		}
		
		// Set color
		gToUse.setColor(color);
		gToUse.setPaint(color);
		
		// Set alpha
        gToUse.setComposite(AlphaComposite.getInstance(alphaMode, alpha));
		
		// Turn on anti aliasing and render with highest possible quality
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        // Update local transform
        Transform t = getComponentFromEntity(Transform.class);
        transform.setToIdentity();
        transform.translate(t.getPosition().get(Vector.X), t.getPosition().get(Vector.Y));
        transform.scale(t.getScale().get(Vector.X), t.getScale().get(Vector.Y));
        transform.rotate(t.getRotation().get(Vector.X), t.getRotation().get(Vector.Y));
        
        // Locally transform the object
        gToUse.transform(transform);
        
		return this;
	}
	
	/**
	 * Sets the color of the {@see IDrawable}.
	 * @param color The {@see Color} to set
	 * @return The {@see IDrawable} itself, for chaining.
	 */
	@Override
	public IDrawable setColor(Color color) {
		this.color = color;
		return this;
	}

	/**
	 * Sets whether or not to fill the {@see IDrawable}.
	 * @param filled Whether or not to fill the {@see IDrawable}
	 * @return The {@see IDrawable} itself, for chaining.
	 */
	@Override
	public IDrawable setFilled(boolean filled){
		this.filled = filled;
		return this;
	}
	
	/**
	 * @return Whether or not the {@see IDrawable} is filled.
	 */
	@Override
	public boolean isFilled(){
		return filled;
	}
	
	/**
	 * Sets the alpha value.
	 * @param alpha The alpha value
	 * @return The {@see IDrawable} itself, for chaining.
	 */
	@Override
	public IDrawable setAlpha(float alpha) {
		this.alpha = alpha;
		return this;
	}
	
	/**
	 * Sets the alpha mode of the {@see IDrawable}.
	 * @param alphaMode The alpha mode
	 * @return The {@see IDrawable} itself, for chaining.
	 */
	@Override
	public IDrawable setAlphaMode(int alphaMode){
		this.alphaMode = alphaMode;
		return this;
	}

	/**
	 * 
	 * @param width
	 * @param strokeStyle
	 * @param join
	 * @param offset
	 * @param pattern
	 * @return The {@see IDrawable} itself, for chaining.
	 */
	@Override
	public IDrawable setStroke(float width, int strokeStyle, int join, float offset, float... pattern) {
		stroke = new BasicStroke(width, strokeStyle, join, 1.0f, pattern, offset);
		return this;
	}
}
