package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import utility.Reports;

public class GooglePage extends MasterPage {

	@FindBy(how = How.XPATH, using = "//*[@id=\"tsf\"]/div[2]/div/div[1]/div/div[1]/input")
	public WebElement searchBox;	
	
	@FindBy(how = How.NAME, using = "btnK")
	public WebElement s;
	
	
	public GooglePage(WebDriver driver, Reports report) {
		super(driver, report);
	}
	
	public void dosomeOps() {
		try {
		
			enterElementValue(searchBox, "hello world");
			searchBox.sendKeys(Keys.ENTER);
			report.reportPassEvent("entered text");
			
			//clickElement(s);
			//report.reportPassEvent("clicked successfully");
			Thread.sleep(1000);
			driver.navigate().to("https://www.yahoo.com");
			
			Thread.sleep(10000);
		}
		
		catch (Exception ex) {
			report.reportWarningEvent(ex.getMessage());
		}
	}

	

}
