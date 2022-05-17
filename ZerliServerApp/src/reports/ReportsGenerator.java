package reports;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import javax.imageio.ImageIO;

import org.apache.pdfbox.*;
import org.apache.pdfbox.contentstream.operator.text.SetTextLeading;
import org.apache.pdfbox.debugger.ui.ImageUtil;
import org.apache.pdfbox.jbig2.segments.Table;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.documentinterchange.taggedpdf.PDLayoutAttributeObject;
import org.apache.pdfbox.pdmodel.font.*;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import org.apache.pdfbox.util.Matrix;
import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler.ChartTheme;
import org.knowm.xchart.style.Styler.LegendPosition;
import org.knowm.xchart.style.markers.SeriesMarkers;

public class ReportsGenerator {
	  
	final int WIDTH = 595;
	final int HEIGHT = 842;
	String[] months = new String[] {"January","February","March","April","May","June","July","August","September","October","November","December"};
	private BufferedImage createBarChartIncome(int width,int height,int quarter)
	{
		quarter *= 3;
	    CategoryChart chart = new CategoryChartBuilder().width((int)(height)).height((int)(width * 0.7f)).title("Quarterly CEO Income Report").xAxisTitle("Month").yAxisTitle("Income").build();
	    
	    // Customize Chart
	    chart.getStyler().setLegendPosition(LegendPosition.OutsideS);
	    //chart.getStyler().setHasAnnotations(true);
	 
	    // Series
	    chart.addSeries(months[quarter], Arrays.asList(new String[] {" "}), Arrays.asList(new Integer[] {15}));
	    chart.addSeries(months[quarter+1], Arrays.asList(new String[] { " "}), Arrays.asList(new Integer[] {5}));
	    chart.addSeries(months[quarter+2], Arrays.asList(new String[] { " "}), Arrays.asList(new Integer[] {10}));
		
	    return ImageUtil.getRotatedImage(BitmapEncoder.getBufferedImage(chart), 90) ;
	}
	private BufferedImage createBarChartComplaints(int width,int height,int quarter)
	{
		quarter *= 3;
	    CategoryChart chart = new CategoryChartBuilder().width((int)(height)).height((int)(width * 0.7f)).title("Quarterly Customer Service Report").xAxisTitle("Month").yAxisTitle("Number of complaints").build();
	    
	    // Customize Chart
	    chart.getStyler().setLegendPosition(LegendPosition.OutsideS);
	    //chart.getStyler().setHasAnnotations(true);
	 
	    // Series
	    chart.addSeries(months[quarter], Arrays.asList(new String[] {" "}), Arrays.asList(new Integer[] {15}));
	    chart.addSeries(months[quarter+1], Arrays.asList(new String[] { " "}), Arrays.asList(new Integer[] {5}));
	    chart.addSeries(months[quarter+2], Arrays.asList(new String[] { " "}), Arrays.asList(new Integer[] {10}));
		
	    return ImageUtil.getRotatedImage(BitmapEncoder.getBufferedImage(chart), 90) ;
	}
	private BufferedImage createBarChartOrder(int width,int height)
	{
	    CategoryChart chart = new CategoryChartBuilder().width((int)(height)).height((int)(width * 0.7f)).title("Order Report").xAxisTitle("Item Type").yAxisTitle("Number of orders").build();
	    
	    // Customize Chart
	    chart.getStyler().setLegendPosition(LegendPosition.OutsideS);
	    //chart.getStyler().setHasAnnotations(true);
	 
	    // Series
	    chart.addSeries("Premade", Arrays.asList(new String[] { "Premade Products"}), Arrays.asList(new Integer[] {15}));
	    chart.addSeries("Custom", Arrays.asList(new String[] { "Custom Defined Items"}), Arrays.asList(new Integer[] {5}));
	    chart.addSeries("Self Created Products", Arrays.asList(new String[] { "Self Created Products"}), Arrays.asList(new Integer[] {10}));
		
	    return ImageUtil.getRotatedImage(BitmapEncoder.getBufferedImage(chart), 90) ;
	}
	private BufferedImage createHistogram(int width,int height)
	{
	    // Create Chart
	    CategoryChart chart = new CategoryChartBuilder().width(width).height(height).title("Score Histogram").xAxisTitle("Mean").yAxisTitle("Count").build();
	    // Customize Chart
	    chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
	    chart.getStyler().setAvailableSpaceFill(.96);
	    chart.getStyler().setOverlapped(true);
	    // Series
	    ArrayList<Integer> a = new ArrayList<Integer>();
	    for(int i = 0;i<20;i++)
	    	a.add(i);
	    Histogram histogram1 = new Histogram(a, 20, -20, 20);
	    //Histogram histogram2 = new Histogram(getGaussianData(5000), 20, -20, 20);
	    chart.addSeries("histogram 1", histogram1.getxAxisData(), histogram1.getyAxisData());
	    //chart.addSeries("histogram 2", histogram2.getxAxisData(), histogram2.getyAxisData());
	    return BitmapEncoder.getBufferedImage(chart);
	}
	
	@SuppressWarnings("unused")
	private BufferedImage createChart(int width, int height) {
		  XYChart chart = new XYChartBuilder().xAxisTitle("X").yAxisTitle("Y").width(width).height(height)
		      .theme(ChartTheme.Matlab).build();
		  XYSeries series = chart.addSeries("Random", null, getRandomNumbers(200));
		  series.setMarker(SeriesMarkers.NONE);
		  return BitmapEncoder.getBufferedImage(chart);
		}
		 
		private double[] getRandomNumbers(int numPoints) {
		  double[] y = new double[numPoints];
		  for (int i = 0; i < y.length; i++) {
		    y[i] = ThreadLocalRandom.current().nextDouble(0, 1000);
		  }
		  return y;
		}
		
		
	public byte[] createOrderReportHistogram(String path,String name,String branch,String startDate,String endDate) {
		    try (PDDocument document = new PDDocument()) {
		      PDPage page = new PDPage(PDRectangle.A4);
		      page.setRotation(0);
		 
		      float pageWidth = page.getMediaBox().getWidth();
		      float pageHeight = page.getMediaBox().getHeight();
		      PDPageContentStream contentStream = new PDPageContentStream(document, page);
		      InputStream stream = getClass().getResourceAsStream("/png/ZerliLogo.png");
		      PDImageXObject logoImage = JPEGFactory.createFromImage(document, ImageIO.read(stream));
		      //contentStream.transform(new Matrix(0, 1, -1, 0, pageWidth, 0));
		      contentStream.drawImage(logoImage, 5, HEIGHT-80,150,80); 
		      contentStream.beginText();
		      contentStream.newLineAtOffset(WIDTH/2 - 100, 760);  
		      contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 30);
		      contentStream.showText("Order Report");
		      contentStream.endText();
		      
		      contentStream.beginText();
		      contentStream.newLineAtOffset(80, 715);  
		      contentStream.setLeading(20);
		      contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER),16);
		      contentStream.showText(String.format("Created for branch %s" ,branch));
		      contentStream.newLine();
		      contentStream.showText(String.format("Shown Month: %s ~ %s",startDate,endDate));
		      contentStream.endText();
		      PDImageXObject chartImage = JPEGFactory.createFromImage(document,
		    		  createBarChartOrder((int) pageHeight, (int) pageWidth));
		      contentStream.transform(new Matrix(0, 1, -1, 0, pageWidth, 0));
		      contentStream.drawImage(chartImage, 80, 0); 
		      contentStream.close();
		      document.addPage(page);
		 
		      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		      document.save(byteArrayOutputStream);
		      File f = new File(path + "//" + name + ".pdf");
		      FileOutputStream fs = new FileOutputStream(f);
		      BufferedOutputStream fileWriter = new BufferedOutputStream(fs);
		      fileWriter.write(byteArrayOutputStream.toByteArray());
		      fileWriter.close();
		      fs.close();
		      
		      return byteArrayOutputStream.toByteArray();
		    }
		    catch(IOException e)
		    {
		    	e.printStackTrace();
		    }
			return null;
	}
	public byte[] createIncomeReportTable(String path,String name,String branch,String startDate,String endDate) {
	    try (PDDocument document = new PDDocument()) {
	      PDPage page = new PDPage(PDRectangle.A4);
	      page.setRotation(0);
	 
	      float pageWidth = page.getMediaBox().getWidth();
	      float pageHeight = page.getMediaBox().getHeight();
	      PDPageContentStream contentStream = new PDPageContentStream(document, page);
	      InputStream stream = getClass().getResourceAsStream("/png/ZerliLogo.png");
	      PDImageXObject logoImage = JPEGFactory.createFromImage(document, ImageIO.read(stream));
	      //contentStream.transform(new Matrix(0, 1, -1, 0, pageWidth, 0));
	      contentStream.drawImage(logoImage, 5, HEIGHT-80,150,80); 
	      contentStream.beginText();
	      contentStream.newLineAtOffset(WIDTH/2 - 100, 760);  
	      contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 30);
	      contentStream.showText("Income Report");
	      contentStream.endText();
	      
	      contentStream.beginText();
	      contentStream.newLineAtOffset(80, 715);  
	      contentStream.setLeading(20);
	      contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER),16);
	      contentStream.showText(String.format("Created for branch %s" ,branch));
	      contentStream.newLine();
	      contentStream.showText(String.format("Shown Month: %s ~ %s",startDate,endDate));
	      contentStream.endText();
	      
//	      PDImageXObject chartImage = JPEGFactory.createFromImage(document,
//	    		  createBarChart((int) pageHeight, (int) pageWidth));
//	      contentStream.transform(new Matrix(0, 1, -1, 0, pageWidth, 0));
//	      contentStream.drawImage(chartImage, 80, 0); 
	      drawTable(contentStream,5,2,50,650,250,30,new String[] {"Date","Income"});
	      contentStream.close();
	      document.addPage(page);
	 
	      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	      document.save(byteArrayOutputStream);
	      File f = new File(path + "//" + name + ".pdf");
	      FileOutputStream fs = new FileOutputStream(f);
	      BufferedOutputStream fileWriter = new BufferedOutputStream(fs);
	      fileWriter.write(byteArrayOutputStream.toByteArray());
	      fileWriter.close();
	      fs.close();
	      
	      return byteArrayOutputStream.toByteArray();
	    }
	    catch(IOException e)
	    {
	    	e.printStackTrace();
	    }
		return null;
}
	private void drawTable(PDPageContentStream contentStream,int cols,int rows,int x,
			int y,int cellwidth,int cellheight,String[] header)
	{
		int tempx;
		int tempy = y;
		for(int i = 1;i<=cols;i++)
		{
			tempx = x;
			for(int j = 1;j<=rows;j++)
			{
				try {
					contentStream.addRect(tempx,tempy,cellwidth,-cellheight);
					contentStream.beginText();
					contentStream.newLineAtOffset(tempx+10, tempy-cellheight+10);
					contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 18);
					if(i == 1)
					contentStream.showText(header[j-1]);
					contentStream.endText();
					tempx += cellwidth;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			tempy -= cellheight;
		}
		try {
			contentStream.stroke();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public byte[] createComplaintReportHistogram(String path,String name,String branch,String startDate,String endDate) {
	    try (PDDocument document = new PDDocument()) {
	      PDPage page = new PDPage(PDRectangle.A4);
	      page.setRotation(0);
	 
	      float pageWidth = page.getMediaBox().getWidth();
	      float pageHeight = page.getMediaBox().getHeight();
	      PDPageContentStream contentStream = new PDPageContentStream(document, page);
	      InputStream stream = getClass().getResourceAsStream("/png/ZerliLogo.png");
	      PDImageXObject logoImage = JPEGFactory.createFromImage(document, ImageIO.read(stream));
	      //contentStream.transform(new Matrix(0, 1, -1, 0, pageWidth, 0));
	      contentStream.drawImage(logoImage, 5, HEIGHT-80,150,80); 
	      contentStream.beginText();
	      contentStream.newLineAtOffset(WIDTH/2 - 100, 760);  
	      contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 30);
	      contentStream.showText("Order Report");
	      contentStream.endText();
	      
	      contentStream.beginText();
	      contentStream.newLineAtOffset(80, 715);  
	      contentStream.setLeading(20);
	      contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER),16);
	      contentStream.showText(String.format("Created for branch %s" ,branch));
	      contentStream.newLine();
	      contentStream.showText(String.format("Shown Month: %s ~ %s",startDate,endDate));
	      contentStream.endText();
	      PDImageXObject chartImage = JPEGFactory.createFromImage(document,
	    		  createBarChartComplaints((int) pageHeight, (int) pageWidth,1));
	      contentStream.transform(new Matrix(0, 1, -1, 0, pageWidth, 0));
	      contentStream.drawImage(chartImage, 80, 0); 
	      contentStream.close();
	      document.addPage(page);
	 
	      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	      document.save(byteArrayOutputStream);
	      File f = new File(path + "//" + name + ".pdf");
	      FileOutputStream fs = new FileOutputStream(f);
	      BufferedOutputStream fileWriter = new BufferedOutputStream(fs);
	      fileWriter.write(byteArrayOutputStream.toByteArray());
	      fileWriter.close();
	      fs.close();
	      
	      return byteArrayOutputStream.toByteArray();
	    }
	    catch(IOException e)
	    {
	    	e.printStackTrace();
	    }
		return null;
}
	public byte[] createIncomeReportTableCEO(String path,String name,String branch,String startDate,String endDate) {
	    try (PDDocument document = new PDDocument()) {
	      PDPage page = new PDPage(PDRectangle.A4);
	      page.setRotation(0);
	 
	      float pageWidth = page.getMediaBox().getWidth();
	      float pageHeight = page.getMediaBox().getHeight();
	      PDPageContentStream contentStream = new PDPageContentStream(document, page);
	      InputStream stream = getClass().getResourceAsStream("/png/ZerliLogo.png");
	      PDImageXObject logoImage = JPEGFactory.createFromImage(document, ImageIO.read(stream));
	      //contentStream.transform(new Matrix(0, 1, -1, 0, pageWidth, 0));
	      contentStream.drawImage(logoImage, 5, HEIGHT-80,150,80); 
	      contentStream.beginText();
	      contentStream.newLineAtOffset(WIDTH/2 - 100, 760);  
	      contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 30);
	      contentStream.showText("Income Report");
	      contentStream.endText();
	      
	      contentStream.beginText();
	      contentStream.newLineAtOffset(80, 715);  
	      contentStream.setLeading(20);
	      contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER),16);
	      contentStream.showText(String.format("Created for branch %s" ,branch));
	      contentStream.newLine();
	      contentStream.showText(String.format("Shown Month: %s ~ %s",startDate,endDate));
	      contentStream.endText();
	      
	      
	      contentStream.close();
	      
	      document.addPage(page);
	 
	      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	      document.save(byteArrayOutputStream);
	      File f = new File(path + "//" + name + ".pdf");
	      FileOutputStream fs = new FileOutputStream(f);
	      BufferedOutputStream fileWriter = new BufferedOutputStream(fs);
	      fileWriter.write(byteArrayOutputStream.toByteArray());
	      fileWriter.close();
	      fs.close();
	      
	      return byteArrayOutputStream.toByteArray();
	    }
	    catch(IOException e)
	    {
	    	e.printStackTrace();
	    }
		return null;
}
}
