package reports;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import Entities.ItemType;
import Entities.ReportType;
import server.Server;

// TODO: Auto-generated Javadoc
/**
 * The Class ReportGenerator is used to generate quarterly reports and monthly reports
 * This class is dependent on the PDF Generator class.
 * Essentially this class is the data gatherer for the PDF Generator
 */
public class ReportGenerator {
	
	/** The Constant QUARTER_MARKS is the definition of a quarter beginning. */
	private static final List<Integer> QUARTER_MARKS = Arrays.asList(new Integer[] {1,4,7,10});
	
	/** The generator used for generating the PDF. */
	static PDFGenerator generator = new PDFGenerator();
	
	/**
	 * Generate monthly report.
	 *
	 * @param reportType the report type the needs to be generated
	 * @param branch the branch that is the report is being generated for/on
	 * @param date the date the monthly report is being generated for
	 */
	public static void GenerateMonthlyReport(ReportType reportType, String branch,LocalDate date)
	{
		//date = date.minusMonths(1);
		Server.Log("Report Generator", "Generating for: " + date.toString());
		if(reportType == ReportType.income)
		{
			ArrayList<Integer[]> dailyIncomeData = new ArrayList<Integer[]>();
			ArrayList<LocalDate> dates = new ArrayList<LocalDate>();
			for(int day = 1;day <= date.lengthOfMonth(); day++)
			{
				Integer[] res = Server.SqlServerManager.GetDailyFinancialIncomeForBranch
				(branch, java.sql.Date.valueOf(LocalDate.of(date.getYear(), date.getMonth(), day)));
				if(res[2] != 0)
				{
					dates.add(LocalDate.of(date.getYear(),date.getMonth() , day));
					dailyIncomeData.add(res);
				}
			}
			byte[] myPDF = null;
			myPDF = generator.createIncomeReportTable(branch, date.getMonth().toString() + "/" + date.getYear(), dailyIncomeData,dates);
			Server.SqlServerManager.InsertReport(ReportType.income, true, branch, java.sql.Date.valueOf(LocalDate.of(date.getYear(), date.getMonthValue(),1)), myPDF);
				
		}
		else if(reportType == ReportType.order)
		{
			HashMap<ItemType,Integer> histogram = new HashMap<>();
			for(ItemType t : ItemType.values())
				histogram.put(t, Server.SqlServerManager.GetOrderCountWithItemWithinPeriod(t,java.sql.Date.valueOf(LocalDate.of(date.getYear(), date.getMonthValue(),1)),java.sql.Date.valueOf(LocalDate.of(date.getYear(), date.getMonthValue(),date.lengthOfMonth()))));
			byte[] myPDF = null;
			myPDF = generator.createOrderReportHistogram(branch, date.getMonth().toString() + "/" + date.getYear(),histogram);
			Server.SqlServerManager.InsertReport(ReportType.order, true, branch, java.sql.Date.valueOf(LocalDate.of(date.getYear(), date.getMonthValue(),1)), myPDF);
		}
		else if(reportType == ReportType.service)
		{
			
		}
	}
	
	/**
	 * Generate quarterly report.
	 *
	 * @param reportType the report type the needs to be generated
	 * @param branch the branch that is the report is being generated for/on
	 * @param date the quarter the quarterly report is being generated for,
	 *  ex: 1/1/2022 is the 1st quarter of 2022, 1/2/2022 is the 2st quarter of 2022
	 */
	public static void GenerateQuarterlyReport(ReportType reportType, String branch,LocalDate date)
	{
		//convert to actual quarter
		int quarter = date.getMonthValue();
		date = LocalDate.of(date.getYear(), QUARTER_MARKS.get(date.getMonthValue()-1), 1);
		LocalDate quarterEnd = date.plusMonths(3);
		quarterEnd.minusDays(1);
		if(reportType == ReportType.income)
		{
			ArrayList<Integer[]> monthlyIncomeData = new ArrayList<Integer[]>();
			ArrayList<LocalDate> dates = new ArrayList<LocalDate>();
			LocalDate temp = LocalDate.of(date.getYear(), date.getMonthValue(), 1);
			while(temp.isBefore(quarterEnd))
			{
				dates.add(LocalDate.of(temp.getYear(),temp.getMonth() , temp.lengthOfMonth()));
				int com = 0,ref = 0,tot = 0;
				for(int day = 1;day <= temp.lengthOfMonth(); day++)
				{
					//if(day == 29 && temp.getMonth() == Month.FEBRUARY && !temp.isLeapYear()) continue;
					Integer[] res = Server.SqlServerManager.GetDailyFinancialIncomeForBranch
					(branch, java.sql.Date.valueOf(LocalDate.of(temp.getYear(), temp.getMonth(), day)));
					com += res[0];
					ref += res[1];
					tot += res[2];
				}
				monthlyIncomeData.add(new Integer[] {com,ref,tot});
				temp = temp.plusMonths(1);
			}
			byte[] myPDF = null;
			myPDF = generator.createIncomeReportTable(branch, "Quarter " + quarter + "/" + date.getYear(), monthlyIncomeData,dates);
			Server.SqlServerManager.InsertReport(ReportType.income, false, branch, java.sql.Date.valueOf(LocalDate.of(date.getYear(), quarter,1)), myPDF);
			
		}
		else if(reportType == ReportType.order)
		{
			
		}
		else if(reportType == ReportType.service)
		{
			
		}
	}
}
