package controllers;

import java.awt.geom.AffineTransform;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import org.apache.pdfbox.debugger.ui.ImageUtil;
import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.util.Matrix;
import org.knowm.xchart.BitmapEncoder;

import Entities.Survey;
import ProtocolHandler.RequestType;
import ProtocolHandler.Handlers.ImageFromServerHandler;
import client.ClientApp;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class SurveyHistogramController implements UserControl {

		@FXML
    	private ImageView checkBox;
		
		@FXML
	    private TextField PdfTextBox;
	   
	    @FXML
	    private ImageView Image1;

	    @FXML
	    private ImageView Image2;

	    @FXML
	    private ImageView Image3;

	    @FXML
	    private ImageView Image4;

	    @FXML
	    private ImageView Image5;

	    @FXML
	    private ImageView Image6;

	    @FXML
	    private Label q1Label;

	    @FXML
	    private Label q2Label;

	    @FXML
	    private Label q3Label;

	    @FXML
	    private Label q4Label;

	    @FXML
	    private Label q5Label;

	    @FXML
	    private Label q6Label;
	    
	    @FXML
	    private AnchorPane activePanelContainer;

	    @FXML
	    private Label surveyNameLabel;

		private Survey survey=new Survey();
		private Image imegeTest1;
		private Image imegeTest2;
		private Image imegeTest3;
		private Image imegeTest4;

		private Image imegeTest5;

		private Image imegeTest6;

	@Override
	public void onEnter() {
		checkBox.setVisible(false);
		ArrayList<byte[]> listOfImages;
		String string =(String)LoginController.windowControl.peekPipe("surveyForExpert");
		survey.setId(Integer.parseInt(string.substring(0,string.indexOf(" "))));
		survey.setContent(string.substring(string.indexOf(" ")+1));
		surveyNameLabel.setText(survey.getContent()+"'s histograms");
		
		ClientApp.ProtocolHandler.Invoke(RequestType.GetSurvey, survey.getId(), null, true);
		survey=(Survey)ClientApp.ProtocolHandler.GetResponse(RequestType.GetSurvey);
		ClientApp.ProtocolHandler.Invoke(RequestType.GetSurveyHistogram, survey, null, true);
		listOfImages=(( ArrayList<byte[]>)ClientApp.ProtocolHandler.GetResponse(RequestType.GetSurveyHistogram));
		
		imegeTest1=(new Image (new ByteArrayInputStream(listOfImages.get(0))));
		Image1.setImage(imegeTest1);
		//q1Label.setText(survey.getQuestions()[0]);
		
		imegeTest2=(new Image (new ByteArrayInputStream(listOfImages.get(1))));
		Image2.setImage(imegeTest2);
		//q2Label.setText(survey.getQuestions()[1]);
		
		imegeTest3=(new Image (new ByteArrayInputStream(listOfImages.get(2))));
		Image3.setImage(imegeTest3);
		//q3Label.setText(survey.getQuestions()[2]);
		
		imegeTest4=(new Image (new ByteArrayInputStream(listOfImages.get(3))));
		Image4.setImage(imegeTest4);
		//q4Label.setText(survey.getQuestions()[3]);
		
		imegeTest5=(new Image (new ByteArrayInputStream(listOfImages.get(4))));
		Image5.setImage(imegeTest5);
		//q5Label.setText(survey.getQuestions()[4]);
		
		imegeTest6=(new Image (new ByteArrayInputStream(listOfImages.get(5))));
		Image6.setImage(imegeTest6);
		//q6Label.setText(survey.getQuestions()[5]);
	}
	
	  @FXML
	    void BackBtnPressed(ActionEvent event) {
		  LoginController.windowControl.setUserControl("/gui/usercontrols/ExpertHomePage.fxml");
	    }
	  
	  @FXML
	    void InsertPDFBtnpressed(ActionEvent event) {
		  
		  	ArrayList<byte[]> listOfPDFArrayList=new ArrayList<byte[]>();
	    	FileChooser PDFFile = new FileChooser();
	    	PDFFile.getExtensionFilters().add(new ExtensionFilter("PDF Files", "*.pdf", "*.PDF"));
	    	ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	        //Show open file dialog
	        File file = PDFFile.showOpenDialog(null);
	        FileInputStream inputStream = null;
	        byte[] data2=null;
	       if (file != null) 
	        {
	        	try 
	        	{
	        		inputStream = new FileInputStream(file);
	        		int nRead;
	          	   	data2 = new byte[(int) file.length()];
	          	   	while ((nRead = inputStream.read(data2, 0, data2.length)) != -1) 
	          	   	{
	          	   		buffer.write(data2, 0, nRead);
	          	   	}
	          	  
				} 
	            catch (Exception e) 
	            {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	
	        	byte[] data1= createPDF("C:\\Users\\Ido Mialy\\Desktop");	
	        	listOfPDFArrayList.add(data1);
	        	listOfPDFArrayList.add(data2);
	         	checkBox.setVisible(true);
	     		PdfTextBox.setText(file.getPath());
	        	ClientApp.ProtocolHandler.Invoke(RequestType.SavePDF, listOfPDFArrayList, survey, false);
	        }
	    }
	@Override
	public void onExit() {
		// TODO Auto-generated method stub
		
	}
	public byte[] createPDF(String path) {
	    try (PDDocument document = new PDDocument()) {
	      PDPage page = new PDPage(PDRectangle.A4);
	      page.setRotation(0);
	 
	      float pageWidth = page.getMediaBox().getWidth();
	      float pageHeight = page.getMediaBox().getHeight();
	      PDPageContentStream contentStream = new PDPageContentStream(document, page);
	      InputStream stream = getClass().getResourceAsStream("/png/ZerliLogo.png");
	      PDImageXObject logoImage = JPEGFactory.createFromImage(document, ImageIO.read(stream));
	      //contentStream.transform(new Matrix(0, 1, -1, 0, pageWidth, 0));
	      contentStream.drawImage(logoImage, 5, pageHeight-80,150,80);
	      //contentStream.drawImage(logoImage, 225, 750,150,80); 
	      contentStream.beginText();
	      contentStream.newLineAtOffset(200, 715);  
    	  contentStream.setFont(PDType0Font.load(document, new File("C:\\Windows\\Fonts\\arial.ttf")), 30);
	      contentStream.showText(survey.getContent()+"'s histogram"); 
	      contentStream.endText();
	      
	      contentStream.beginText();
	      contentStream.newLineAtOffset(150, 15);  
	      contentStream.setLeading(20);
    	  contentStream.setFont(PDType0Font.load(document, new File("C:\\Windows\\Fonts\\arial.ttf")), 16);
	      contentStream.showText("All Rights Reserved To Group VI 2022 ©");
	      contentStream.newLine();
	     // contentStream.showText(String.format("To Group VI 2022 ©"));
	      contentStream.endText();
	      
	      PDImageXObject image1 = JPEGFactory.createFromImage(document,SwingFXUtils.fromFXImage(imegeTest1, null));
	      AffineTransform at1 = new AffineTransform(250, 0, 0, 200, 30, 500);
	      at1.rotate(Math.toRadians(360));
	      contentStream.drawXObject(image1, at1);
	      
	      PDImageXObject image2 = JPEGFactory.createFromImage(document,SwingFXUtils.fromFXImage(imegeTest2, null));
	      AffineTransform at2 = new AffineTransform(250, 0, 0, 200, 310, 500);
	      at2.rotate(Math.toRadians(360));
	      contentStream.drawXObject(image2, at2);
	      
	      PDImageXObject image3 = JPEGFactory.createFromImage(document,SwingFXUtils.fromFXImage(imegeTest3, null));
	      AffineTransform at3 = new AffineTransform(250, 0, 0, 200, 30, 270);
	      at1.rotate(Math.toRadians(360));
	      contentStream.drawXObject(image3, at3);
	      
	      PDImageXObject image4 = JPEGFactory.createFromImage(document,SwingFXUtils.fromFXImage(imegeTest4, null));
	      AffineTransform at4 = new AffineTransform(250, 0, 0, 200, 310, 270);
	      at2.rotate(Math.toRadians(360));
	      contentStream.drawXObject(image4, at4);
	     
	      PDImageXObject image5 = JPEGFactory.createFromImage(document,SwingFXUtils.fromFXImage(imegeTest5, null));
	      AffineTransform at5 = new AffineTransform(250, 0, 0, 200, 30, 40);
	      at1.rotate(Math.toRadians(360));
	      contentStream.drawXObject(image5, at5);
	      
	      PDImageXObject image6 = JPEGFactory.createFromImage(document,SwingFXUtils.fromFXImage(imegeTest6, null));
	      AffineTransform at6 = new AffineTransform(250, 0, 0, 200, 310, 40);
	      at2.rotate(Math.toRadians(360));
	      contentStream.drawXObject(image6, at6);
	      
	      contentStream.transform(new Matrix(0, 1, -1, 0, pageWidth, 0));
	      contentStream.close();
	      document.addPage(page);
	 
	      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	      document.save(byteArrayOutputStream);
	      
		   /*
		  // THIS IS FILE SAVING FOR DEBUG 
		  File f = new File(path + "//" + "try1" + ".pdf");
		  FileOutputStream fs = new FileOutputStream(f);
          BufferedOutputStream fileWriter = new BufferedOutputStream(fs);
          fileWriter.write(byteArrayOutputStream.toByteArray());
		  fileWriter.close();
		  fs.close();
		  // FILE SAVING FOR DEBUG END
		  */
		  
	      return byteArrayOutputStream.toByteArray();
	    }
	    catch(IOException e)
	    {
	    	e.printStackTrace();
	    }
		return null;
}

    @FXML
    void submitPDFBtnpressed(ActionEvent event) {
    	if(!PdfTextBox.getText().equals(""))
    	{
    		checkBox.setVisible(false);
    		PdfTextBox.setText("");
    		 LoginController.windowControl.setUserControl("/gui/usercontrols/ExpertHomePage.fxml");
    	}
    	
    }
}
