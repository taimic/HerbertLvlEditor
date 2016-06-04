import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JRootPane;

import scenes.Scene;
import utils.messaging.Message;
import utils.messaging.Messenger;

public class LevelEditor extends JFrame{
	/**
	 * The serialization ID.
	 */
	private static final long serialVersionUID = -7763834372253763641L;
	/**
	 * The default name of the level editor.
	 */
	public static final String DEFAULT_NAME = "Level Editor";
	/**
	 * The current version of the application.
	 */
	public static final String VERSION = "0.0.1";
	
	/**
	 * The name of the application.
	 */
	private String name;
	/**
	 * The width of the level editor.
	 */
	private int width;
	/**
	 * The height of the level editor.
	 */
	private int height;
	
	/**
	 * The main panel to which the components will be added.
	 */
	private JLayeredPane mainPanel;
	
	/**
	 * Constructor.
	 * @param width The width to use
	 * @param height The height to use
	 */
	public LevelEditor(int width, int height) {
		this(DEFAULT_NAME + " (" + VERSION + ")", width, height);
	}
	
	/**
	 * Constructor.
	 * @param name The name of the application
	 * @param width The width to use
	 * @param height The height to use
	 */
	public LevelEditor(String name, int width, int height) {
		this.name = name;
		this.width = width;
		this.height = height;
		this.mainPanel = new JLayeredPane();
		this.mainPanel.setPreferredSize(new Dimension(width, height));
		this.mainPanel.setBackground(Color.black);
		this.mainPanel.setOpaque(true);
		this.setResizable(true);
		this.add(mainPanel);
		this.setTitle(this.name);
		this.setSize(new Dimension(this.width, this.height));
		this.setVisible(true);
		// Add resize listener
		this.getRootPane().addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
            	Messenger.sendMessage(Message.RESIZE, (float)((JRootPane)e.getSource()).getWidth() / width, (float)((JRootPane)e.getSource()).getHeight() / height);
           }
        });
		// Add close listener
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
            public void windowClosing(WindowEvent e){
				e.getWindow().dispose();
            }
		});
	}
	
	/**
	 * Disposes everything before closing the window.
	 */
	@Override
	public void dispose() {
		Messenger.sendMessage(Message.QUIT);
	    super.dispose();	    
	}
	
	/**
	 * Adds a scene to the layered panel, therefore to the application.
	 * @param scene The scene to add
	 */
	public void add(Scene scene){
		mainPanel.add(scene, new Integer(scene.getZIndex()));
		mainPanel.getParent().revalidate();
		mainPanel.revalidate();
		mainPanel.repaint();
	}
	
	/**
	 * Resizes the {@see JFrame} manually.
	 */
	public void resize(){
		this.getRootPane().setBounds(this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}
}
