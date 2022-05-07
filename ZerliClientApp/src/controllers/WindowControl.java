package controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class WindowControl {

	public static Stage stage;
	public IContainable frameController;
	private UserControl currentControl;

	private Map<String,Parent> map = new HashMap<>();
	private Map<String,UserControl> uc_map = new HashMap<String, UserControl>();
	private Map<String, Object> pipe = new HashMap<>();
	
	public WindowControl(IContainable frameController) {
		this.frameController = frameController;
	}
	
	public void putPipe(String tag,Object data) {
		pipe.put(tag, data);
		
	}
	public Object getPipe(String tag) {
		Object object = pipe.get(tag);
		pipe.remove(tag);
		return object;
	}
	
	public Object peekPipe(String tag) {
		return pipe.get(tag);
	}
	
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
}
