package Utilities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class GenericUtilties {
	public static void SetWindowMovable(Parent parent,Stage stage)
	{
		class TwoInOneEventHandler {
			EventHandler<MouseEvent> mousePressed;
			EventHandler<MouseEvent> mouseDragged;
			private double xOffset = 0; 
			private double yOffset = 0;
			private Stage stage;
			private Parent parent;
			public TwoInOneEventHandler(Parent parent, Stage stage)
			{
				this.stage = stage;
				this.parent = parent;
				mousePressed = new EventHandler<MouseEvent>() {
		            public void handle(MouseEvent event) {
		            	
		                xOffset = event.getSceneX();
		                yOffset = event.getSceneY();
		            }
				};
				mouseDragged = new EventHandler<MouseEvent>() {
		            @Override
		            public void handle(MouseEvent event) {
		                stage.setX(event.getScreenX() - xOffset);
		                stage.setY(event.getScreenY() - yOffset);
		            }
				};
			}
			public EventHandler<MouseEvent> GetPressedEvent()
			{
				return mousePressed;
			}
			
			public EventHandler<MouseEvent> GetDraggedEvent()
			{
				return mouseDragged;
			}
		};
		var DoubledEvents = new TwoInOneEventHandler(parent,stage);
		parent.setOnMousePressed(DoubledEvents.GetPressedEvent()); 
        parent.setOnMouseDragged(DoubledEvents.GetDraggedEvent());
	}
	public static java.sql.Timestamp Convert_LocalDate_To_SQLDate(LocalDate date,LocalTime time)
	{
		return java.sql.Timestamp.valueOf(date.atTime(time));
	}
	
	public static LocalDateTime Convert_LocalDate_To_SQLDate(java.sql.Timestamp datetime)
	{
		return datetime.toLocalDateTime();
	}
}
