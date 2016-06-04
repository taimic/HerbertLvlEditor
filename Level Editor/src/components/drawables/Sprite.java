package components.drawables;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import utils.math.Vector;


public class Sprite extends Drawable{
	/**
	 * The serialization ID.
	 */
	private static final long serialVersionUID = 2059404255043093123L;
	
	/**
	 * The {@see BufferedImage} for reading the file.
	 */
	private transient BufferedImage bufferedImage;
	/**
	 * The {@see BufferedImage} that is being rendered.
	 */
	private transient BufferedImage tintedImage;
	/**
	 * The {@see TexturePaint} for rendering the texture.
	 */
	private transient TexturePaint texturePaint;
	/**
	 * The file name of the file to load.
	 */
	private String fileName;
	/**
	 * The coordinates.
	 */
	private Vector coordinates;
	/**
	 * The maximal height of the {@see Sprite}.
	 */
	private int maxWidth;
	/**
	 * The maximal width of the {@see Sprite}.
	 */
	private int maxHeight;
	/**
	 * For properly positioning the rectangle.
	 */
	private Rectangle positionRectangle;
	
	/**
	 * Default constructor.
	 */
	public Sprite() {
		this.coordinates = Vector.getZeroVector(3);
		positionRectangle = new Rectangle();
	}
	
	/**
	 * Constructor.
	 * @param fileName The file name of the file to load
	 */
	public Sprite(String fileName) {
		this(fileName, 0, 0);
	}
	
	/**
	 * Constructor.
	 * @param fileName The file name of the file to load
	 * @param x The x-coordinate
	 * @param y The y-coordinate
	 */
	public Sprite(String fileName, int x, int y) {
		this();
		this.fileName = fileName;
		
		this.coordinates = this.coordinates.add(x, y, 0);
		
		loadImage();
		this.setCenter();
	}
	
	/**
	 * Updates the required information.
	 */
	public void updateData(){
		this.originalWidth = bufferedImage.getWidth();
		this.originalHeight = bufferedImage.getHeight();
		this.maxWidth = (int)originalWidth;
		this.maxHeight = (int)originalHeight;
		updateTexturePaint();
	}
	
	/**
	 * Updates the texture paint.
	 * @return The {@see IDrawable} itself, for chaining.
	 */
	private IDrawable updateTexturePaint(){
		this.texturePaint = new TexturePaint(tintedImage, new Rectangle((int)positionRectangle.getX(), (int) positionRectangle.getY(), maxWidth, maxHeight));
		return this;
	}
	
	/**
	 * Calculates the center for the specific {@see Drawable}.
	 * @return The {@see IDrawable} itself, for chaining.
	 */
	@Override
	protected IDrawable setCenter(){
		this.center = this.coordinates.add(maxWidth, maxHeight).scale(.5f);
		return this;
	}
	
	/**
	 * Loads the image.
	 * @return The {@see IDrawable} itself, for chaining.
	 */
	public IDrawable loadImage() {
        try {
        	bufferedImage = ImageIO.read(new File(this.fileName));
        	tintedImage = bufferedImage;
        	updateData();
        } catch (IOException ex) {
        	System.err.println("Could not load image: " + this.fileName);
        }
        return this;
    }
	
	/**
	 * Sets the maximal width of the image.
	 * @param maxWidth The maximal width
	 * @return The {@see IDrawable} itself, for chaining.
	 */
	public IDrawable setMaxWidth(int maxWidth){
		this.maxWidth = maxWidth;
		return this;
	}
	
	/**
	 * Sets the maximal height of the image.
	 * @param maxHeight The maximal height
	 * @return The {@see IDrawable} itself, for chaining.
	 */
	public IDrawable setMaxHeight(int maxHeight){
		this.maxHeight = maxHeight;
		return this;
	}
	
	/**
	 * Sets the file by creating a new one using the given file name.
	 * @param fileName The file name
	 */
	public void setFile(String fileName){
		this.fileName = fileName;
		loadImage();
	}
	
	/**
	 * Sets the coordinates of the {@see Sprite}.
	 * @param x The x-coordinate
	 * @param y The y-coordinate
	 */
	public void setCoordinates(int x, int y){
		this.coordinates = new Vector(x, y, 0);
	}
	
	@Override
	public IDrawable setColor(Color color){
		this.color = color;
		this.tintedImage = tint(color, bufferedImage);
		return this;
	}
	
	/**
	 * @return The color.
	 */
	public Color getColor(){
		return this.color;
	}
	
	/**
	 * Renders the {@see IDrawable}.
	 * @param g The {@see Graphics} component that is used for rendering
	 * @return The {@see IDrawable} itself, for chaining.
	 */
	@Override
	public IDrawable render(Graphics g){
		super.render(g);
		
		positionRectangle = new Rectangle(0, 0, maxWidth, maxHeight);
		updateTexturePaint();
		gToUse.setPaint(texturePaint);
		gToUse.fill(positionRectangle);
		
		return this;
	}
	
	/**
	 * Resizes the {@see Sprite}.
	 * @param width The new width
	 * @param height The new height
	 */
	@Override
	public Sprite resize(float width, float height){
		this.setMaxWidth((int) (getOriginalWidth() * width));
		this.setMaxHeight((int) (getOriginalHeight() * height));
		return this;
	}
	
	/**
	 * Convenience method for tinting with a {@see Color}.
	 * @param tintColor The tint color itself.
	 * @param sprite The sprite to tint
	 * @return The tinted {@see BufferedImage}.
	 */
	protected BufferedImage tint(Color tintColor, BufferedImage sprite){
		return this.tint(tintColor.getRed() / 255, tintColor.getGreen() / 255, tintColor.getBlue() / 255, alpha, sprite);
	}
	
	/**
	 * Tints the given {@see BufferedImage} with the given values.
	 * @param r The value of the red component in a range of [0..1]
	 * @param g The value of the green component in a range of [0..1]
	 * @param b The value of the blue component in a range of [0..1]
	 * @param a The value of the alpha component in a range of [0..1]
	 * @param sprite The sprite to tint
	 * @return The tinted {@see BufferedImage}.
	 */
	protected BufferedImage tint(float r, float g, float b, float a, BufferedImage sprite){
		BufferedImage tintedSprite = new BufferedImage(sprite.getWidth(), sprite.getHeight(), BufferedImage.TRANSLUCENT);
		Graphics2D graphics = tintedSprite.createGraphics();
		graphics.drawImage(sprite, 0, 0, null);
		graphics.dispose();
		
		int ax, rx, gx, bx;
		
		for (int i = 0; i < tintedSprite.getWidth(); i++){
			for (int j = 0; j < tintedSprite.getHeight(); j++){
				ax = tintedSprite.getColorModel().getAlpha(tintedSprite.getRaster().getDataElements(i, j, null));
				rx = tintedSprite.getColorModel().getRed(tintedSprite.getRaster().getDataElements(i, j, null));
				gx = tintedSprite.getColorModel().getGreen(tintedSprite.getRaster().getDataElements(i, j, null));
				bx = tintedSprite.getColorModel().getBlue(tintedSprite.getRaster().getDataElements(i, j, null));
				rx *= r;
				gx *= g;
				bx *= b;
				ax *= a;
				tintedSprite.setRGB(i, j, (ax << 24) | (rx << 16) | (gx << 8) | (bx));
			}
		}
		return tintedSprite;
	}
}
