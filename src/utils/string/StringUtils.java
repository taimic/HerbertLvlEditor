package utils.string;

public class StringUtils {
	/**
	 * @param string The string to capitalize
	 * @return The capitalized string.
	 */
	public static String capitalize(final String string){
		return Character.toUpperCase(string.charAt(0)) + string.substring(1);
	}
}
