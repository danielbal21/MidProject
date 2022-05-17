package server;

import java.io.IOException;
import controllers.StartSQLPassswordConfigController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ServerApp extends Application{ 
	
	final public static int DEFAULT_PORT = 5555;
	public static Server server = new Server(DEFAULT_PORT) ;
	
	public static void main(String[] args) {
		/*ReportsGenerator rg = new ReportsGenerator();
		//rg.createOrderReportHistogram("C:\\Users\\Del0riuM\\Desktop","check","Haifa","03-2022","04-2022");
		rg.createComplaintReportHistogram("C:\\Users\\Del0riuM\\Desktop","check","Haifa","03-2022","04-2022");
		Desktop desktop = Desktop.getDesktop();
		try {
			desktop.open(new File("C:\\Users\\Del0riuM\\Desktop" + "\\check.pdf"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		new Thread(new Runnable() {

//			@Override
//			public void run() {
//				try {
//					rg.createPdf("C:\\Users\\Del0riuM\\Desktop","check");
//					try {
//						Thread.sleep(4000);
//					} catch (InterruptedException e) {
//						 TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					Desktop desktop = Desktop.getDesktop();
//					desktop.open(new File("C:\\Users\\Del0riuM\\Desktop" + "\\check.pdf"));
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
//			}
//
//		}).run();
 */
        try 
        {
        	server.listen();
        } 
        catch (Exception ex) 
        {
          System.out.println("Failed listen clients!");
        }
        
		launch(args);
	}
	
	@Override
	public void start(Stage stage){
		StartSQLPassswordConfigController SQLpasswordController;
		Parent root = null;
		try 
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/gui/StartSQLPassswordConfig.fxml"));
			root =  loader.load(); 
			Utilities.GenericUtilties.SetWindowMovable(root, stage);
			SQLpasswordController = loader.getController();
			Scene scene = new Scene(root);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.setScene(scene); 
			SQLpasswordController.setStage(stage);
			stage.show();
		} 
		catch (IOException e) 
		{
			System.out.println("Failed load root");
			e.printStackTrace();
		}
	}
	

}