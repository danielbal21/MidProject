/*
 * 
 */
package controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.animation.PauseTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

// TODO: Auto-generated Javadoc
/**
 * The Class WindowControl.
 */
public class WindowControl {

	/** The stage. */
	public static Stage stage;
	
	/** The frame controller. */
	public IContainable frameController;
	
	/** The current control. */
	private UserControl currentControl;

	/** The map. */
	private Map<String,Parent> map = new HashMap<>();
	
	/** The uc map. */
	private Map<String,UserControl> uc_map = new HashMap<String, UserControl>();
	
	/** The pipe. */
	private Map<String, Object> pipe = new HashMap<>();
	
	/**
	 * Instantiates a new window control.
	 *
	 * @param frameController the frame controller
	 */
	public WindowControl(IContainable frameController) {
		this.frameController = frameController;
	}
	
	/**
	 * Put pipe.
	 *
	 * @param tag the tag
	 * @param data the data
	 */
	public void putPipe(String tag,Object data) {
		pipe.put(tag, data);
		
	}
	
	/**
	 * Gets the pipe.
	 *
	 * @param tag the tag
	 * @return the pipe
	 */
	public Object getPipe(String tag) {
		Object object = pipe.get(tag);
		pipe.remove(tag);
		return object;
	}
	
	/**
	 * Peek pipe.
	 *
	 * @param tag the tag
	 * @return the object
	 */
	public Object peekPipe(String tag) {
		return pipe.get(tag);
	}
	
	/**
	 * Sets the user control.
	 *
	 * @param path the new user control
	 */
	public void setUserControl(String path)
	{
		if(currentControl != null) {
			currentControl.onExit();
		}
	
		if(map.get(path) == null ) {
			FXMLLoader loader;
			Parent root;
			loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(path));
			root = null;
			try {root = loader.load();	} 
			catch (IOException e) {e.printStackTrace();} 
			map.put(path, root);
			currentControl = loader.getController();
			uc_map.put(path, currentControl);
			
		}
		else {
			currentControl = uc_map.get(path);
		}
		frameController.getControlContainer().getChildren().clear();
		frameController.getControlContainer().getChildren().add(map.get(path));
		currentControl.onEnter();
	}

	/**
	 * Refresh.
	 */
	public void Refresh() {
		currentControl.onEnter();
		
	}
}
