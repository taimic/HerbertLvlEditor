package utils.math;

public class Aspect {
	/**
	 * @param originalWidth The original width
	 * @param originalHeight The original height
	 * @param height The new height
	 * @return The new width, kept within the aspect ratio.
	 */
	public static float getWidth(float originalWidth, float originalHeight, float height){
		return (height / originalHeight) * originalWidth;
	}
	
	/**
	 * @param originalWidth The original width
	 * @param originalHeight The original height
	 * @param height The new width
	 * @return The new height, kept within the aspect ratio.
	 */
	public static float getHeight(float originalWidth, float originalHeight, float width){
		return (width / originalWidth) * originalHeight;
	}
	
	/**
	 * @param originalWidth The original width
	 * @param originalHeight The original height
	 * @return The aspect ratio.
	 */
	public static float getAspectRatio(float originalWidth, float originalHeight){
		return originalHeight / originalWidth;
	}
}
