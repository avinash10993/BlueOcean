package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import utility.Reports;

public class MasterPage {
	
	public WebDriver driver;
	public Reports report;
	
	public MasterPage(WebDriver driver, Reports report) {
		this.driver = driver;
		this.report = report;
		PageFactory.initElements(driver, this);
	}
	
	public boolean clickElement (WebElement element) {
		try {
			if (element.isDisplayed()) {
				element.click();
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} return false;
	}
	
	public boolean clearElement (WebElement element) {
		try {
			if (element.isDisplayed()) {
				element.clear();
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} return false;
	}
	
	public boolean enterElementValue (WebElement element, String value) {
		try {
			if (element.isDisplayed()) {
				element.sendKeys(value);
				return true;
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} return false;
	}
	
	public boolean selectElementByValue (WebElement element, String value) {
		try {
			if (element.isDisplayed()) {
				Select select = new Select(element);
				select.selectByValue(value);
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} return false;
	}
	
	public String getElementValue (WebElement element) {
		try {
			if (element.isDisplayed()) {
				return element.getText();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} return null;
	}
	
	public boolean waitForElementLoad(WebElement element) {
		try {
			WebDriverWait wait = new WebDriverWait(driver,120);
			wait.until(ExpectedConditions.visibilityOf(element));
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
	
	public boolean waitForElementLoad(By by) {
		try {
			WebDriverWait wait = new WebDriverWait(driver,120);
			wait.until(ExpectedConditions.presenceOfElementLocated(by));
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
	
	public void launchApplication(String url) {
		try {
			driver.navigate().to(url);
			

				report.reportPassEvent("Launched Application");
		} catch (Exception ex) {
			report.reportFailEvent("Unable to launch the application");
			report.reportWarningEvent(ex.getMessage());
		} 
	}
}
