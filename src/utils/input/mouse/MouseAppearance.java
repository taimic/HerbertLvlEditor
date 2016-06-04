package utils.input.mouse;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class MouseAppearance {
	public static final String CUSTOM_CURSOR_PATH = "images/hud/cursors/";
	/**
	 * All custom cursors.
	 */
	public final static Cursor[] CUSTOM_CURSORS = new Cursor[]{
		Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon(CUSTOM_CURSOR_PATH + "Dragable.png").getImage(), new Point(0,0), "Dragable"),
		Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon(CUSTOM_CURSOR_PATH + "Dragging.png").getImage(), new Point(0,0), "Dragging"),
		Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon(CUSTOM_CURSOR_PATH + "GoodLuck.png").getImage(), new Point(0,0), "GoodLuck"),
		Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon(CUSTOM_CURSOR_PATH + "Here.png").getImage(), new Point(0,0), "Here"),
		Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon(CUSTOM_CURSOR_PATH + "Nope.png").getImage(), new Point(0,0), "Nope"),
		Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon(CUSTOM_CURSOR_PATH + "Peace.png").getImage(), new Point(0,0), "Peace"),
		Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon(CUSTOM_CURSOR_PATH + "Success.png").getImage(), new Point(0,0), "Success"),
		Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon(CUSTOM_CURSOR_PATH + "OK.png").getImage(), new Point(0,0), "OK"),
		Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon(CUSTOM_CURSOR_PATH + "Ass.png").getImage(), new Point(0,0), "Ass"),
		Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon(CUSTOM_CURSOR_PATH + "Rocking.png").getImage(), new Point(0,0), "Rocking")
	};
	
	/**
	 * The instance.
	 */
	private static MouseAppearance instance;
	/**
	 * The frame as canvas.
	 */
	private static JFrame window;

	/**
	 * @return A new instance, if there was no instance before. The same instance otherwise.
	 */
	public static MouseAppearance getInstance(){
		if(instance == null) instance = new MouseAppearance();
		return instance;
	}
	
	/**
	 * Sets the window.
	 * @param frame The window
	 */
	public static void setWindow(JFrame frame){
		window = frame;
	}
	
	/**
	 * Sets the cursor.
	 * @param cursor The cursor to set
	 */
	public static void setCursor(Cursor cursor){
		window.setCursor(cursor);
	}
}
