package utility;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.google.common.io.Files;

public class Reports {

	private WebDriver driver;
	private Properties prop;
	private ExtentHtmlReporter htmlReport;
	private ExtentReports eReport;
	private ExtentTest logger;
	private String strTestName, reportFolderName, reportPath;

	public Reports(String strTestName) {
		try {
			this.strTestName = strTestName;
			reportFolderName = strTestName + "_" + fn_getDateTime();
			reportPath = System.getProperty("user.dir") + File.separator + "reports" + File.separator + reportFolderName;			
			new File(reportPath).mkdirs();
			
			htmlReport = new ExtentHtmlReporter(reportPath + File.separator + strTestName + ".html");
			htmlReport.config().setDocumentTitle(strTestName);
			htmlReport.config().setReportName(strTestName);
			
			eReport  = new ExtentReports();
			eReport.attachReporter(htmlReport);
			
			logger = eReport.createTest(strTestName);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public void setProp(Properties prop) {
		this.prop = prop;
	}

	public void reportPassEvent(String details) {
		try {
			Boolean flag = new Boolean(prop.getProperty("enablePassScreenshot"));
			if (flag)
				logger.pass(details, MediaEntityBuilder.createScreenCaptureFromPath(getScreenshot(reportPath, strTestName + "_" + fn_getDateTime()), details).build());
			else
				logger.pass(details);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void reportFailEvent(String details) {
		try {
			Boolean flag = new Boolean(prop.getProperty("enableFailScreenshot"));
			if (flag)
				logger.fail(details, MediaEntityBuilder.createScreenCaptureFromPath(getScreenshot(reportPath, strTestName + "_" + fn_getDateTime()), details).build());
			else
				logger.fail(details);
			Assert.fail(details);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void reportWarningEvent(String details) {
		try {
			Boolean flag = new Boolean(prop.getProperty("enableWarningScreenshot"));
			if (flag)
				logger.warning(details, MediaEntityBuilder.createScreenCaptureFromPath(getScreenshot(reportPath, strTestName + "_" + fn_getDateTime()), details).build());
			else
				logger.warning(details);
			Assert.fail(details);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String fn_getDateTime() {
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH-mm-ss");
			Date date = Calendar.getInstance().getTime();
			String currentDate = dateFormat.format(date);
			return currentDate;
		} catch (Exception ex) {
			ex.printStackTrace();
		} return null;
	}
	
	private String getScreenshot(String filePath, String fileName) {
		try {
			TakesScreenshot scrShot = (TakesScreenshot)driver;
			File srcFile = scrShot.getScreenshotAs(OutputType.FILE);
			String scrShotFile = filePath + File.separator + fileName + ".jpg";
			Files.copy(srcFile, new File(scrShotFile));
			return scrShotFile;
		} catch (Exception ex) {
			ex.printStackTrace();
		} return null;
	}
	
	public void endReport() {
		eReport.flush();
	}
}
