package tests;

import org.testng.annotations.Test;

import pages.GooglePage;
import pages.MasterPage;
import utility.TestConfiguration;

public class SampleScenario extends TestConfiguration {
  
	
@Test
  public void login() 
  {
  
	String url=prop.getProperty("Application_URL");
	
	MasterPage master = new MasterPage(driver, report);
	
	master.launchApplication(url);
	
	GooglePage google=new GooglePage(driver, report);
	google.dosomeOps();
	
	
	driver.quit();
	
  }
}
