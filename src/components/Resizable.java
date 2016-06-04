package components;

import java.util.Map;

import utils.math.Aspect;

public class Resizable extends Component{
	/**
	 * The serialization ID.
	 */
	private static final long serialVersionUID = 6580768724978958944L;
	/**
	 * Holds whether or not to keep the aspect ratio.
	 */
	private boolean keepAspect;
	/**
	 * The components to resize.
	 */
	private Map<Class<? extends Component>, Component> components;
	/**
	 * The width ratio that was calculated according to the components dimensions.
	 */
	private float wR;
	/**
	 * The height ratio that was calculated according to the components dimensions.
	 */
	private float hR;
	/**
	 * The smallest calculated ratio that is actually used for resizing.
	 */
	private float ratio;
	
	private float originalWidth;
	private float originalHeight;
	
	/**
	 * Resizes all components with the entity.
	 * @param width The new width
	 * @param height The new height
	 * @return The {@see Component} itself, for chaining.
	 */
	public Component resize(float width, float height){
		components = this.getEntity().getComponents();
		
		for(Component component : components.values()){
			// Avoid endless loop
			if(component instanceof Resizable) continue;

			// Avoid division by 0
			if(component.getOriginalWidth() == 0 || component.getOriginalHeight() == 0) continue;
			
			if(keepAspect) {
				originalWidth = component.getOriginalWidth();
				originalHeight = component.getOriginalHeight();
				
				wR = Aspect.getWidth(originalWidth, originalHeight, originalHeight * height) / originalWidth;
				hR = Aspect.getHeight(originalWidth, originalHeight, originalWidth * width) / originalHeight;
				
				ratio = wR < hR ? hR : wR;
				
				component.resize(ratio, ratio);
			} else {
				// Resize without considering the aspect ratio
				component.resize(width, height);
			}
		}
		return this;
	}

	/**
	 * @return Whether or not to keep the aspect ratio.
	 */
	public boolean keepAspect() {
		return keepAspect;
	}

	/**
	 * Sets whether or not to keep the aspect ratio.
	 * @param keepAspect Whether or not to keep the aspect ratio.
	 */
	public void setKeepAspect(boolean keepAspect) {
		this.keepAspect = keepAspect;
	}
}
