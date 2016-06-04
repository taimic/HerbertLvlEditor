package components;

import java.awt.Color;

import services.ServiceLocator;
import services.systems.EditorSystem;

import components.drawables.Sprite;

public class Editable extends Component{
	/**
	 * Holds whether or not the {@see Editable} is selected.
	 */
	private boolean selected;
	/**
	 * The tint color that shall be applied to any {@see Sprite} of the parent {@see Entity}.
	 */
	private Color tintColor;
	/**
	 * The default color that shall be applied to any texture of the parent {@see Entity}.
	 */
	private Color defaultColor;
	
	/**
	 * Default constructor.
	 */
	public Editable() {
		super();
		this.tintColor = Color.WHITE;
		this.defaultColor = Color.WHITE;
		ServiceLocator.getService(EditorSystem.class).add(this);
	}

	/**
	 * @return Whether or not this {@see Editable} is selected.
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * Sets this {@see Editable} de-/selected.
	 * @param selected Whether or not to select the {@see Editable}
	 * @return The {@see Editable} itself, for chaining.
	 */
	public Editable setSelected(boolean selected) {
		this.selected = selected;
		return this;
	}
	

	/**
	 * @return The tint color that shall be applied to any {@see Sprite} of the parent {@see Entity}.
	 */
	public Color getTintColor(){
		return this.tintColor;
	}

	/**
	 * Sets the tint color.
	 * @return The {@see Editable} itself, for chaining.
	 */
	public Editable setTintColor(Color tintColor){
		this.tintColor = tintColor;
		return this;
	}
	
	/**
	 * Updates the {@see Editable}s.
	 */
	@Override
	public Editable update(){
		if(getEntity() != null){
			if(selected) getEntity().getComponent(Sprite.class).setColor(tintColor);
			else getEntity().getComponent(Sprite.class).setColor(defaultColor);
		}
		return this;
	}
}
