package components.drawables;
import java.awt.Color;
import java.awt.Graphics;

import utils.math.Vector;


public interface IDrawable {
	/**
	 * Renders the {@see IDrawable}.
	 * @param g The {@see Graphics} component that is used for rendering
	 * @return The {@see IDrawable} itself, for chaining.
	 */
	IDrawable render(Graphics g);
	/**
	 * Sets the color of the {@see IDrawable}.
	 * @param color The {@see Color} to set
	 * @return The {@see IDrawable} itself, for chaining.
	 */
	IDrawable setColor(Color color);
	/**
	 * 
	 * @param width
	 * @param strokeStyle
	 * @param join
	 * @param offset
	 * @param pattern
	 * @return The {@see IDrawable} itself, for chaining.
	 */
	IDrawable setStroke(float width, int strokeStyle, int join, float offset, float... pattern);
	/**
	 * Sets whether or not to fill the {@see IDrawable}.
	 * @param filled Whether or not to fill the {@see IDrawable}
	 * @return The {@see IDrawable} itself, for chaining.
	 */
	IDrawable setFilled(boolean filled);
	/**
	 * @return Whether or not the {@see IDrawable} is filled.
	 */
	boolean isFilled();
	/**
	 * Sets the alpha value.
	 * @param alpha The alpha value
	 * @return The {@see IDrawable} itself, for chaining.
	 */
	IDrawable setAlpha(float alpha);
	/**
	 * Sets the alpha mode of the {@see IDrawable}.
	 * @param alphaMode The alpha mode
	 * @return The {@see IDrawable} itself, for chaining.
	 */
	IDrawable setAlphaMode(int alphaMode);
	
	/**
	 * @return The calculated center point. Since a {@see Point} 
	 * is a {@see IDrawable} this should not be used for chaining.
	 */
	Vector getCenter();
}
