package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.xml.XmlClass;

public class TestConfiguration {

	public Reports report;
	public WebDriver driver;
	public Properties prop;
	public Map<String, String> testData;
	public String strTestName;

	@BeforeTest
	public void beforeTestRun(ITestContext context) {
		// InitializeTestName
		loadTestName(context);
		// InitializeProperties
		loadProperties();
		// InitializeBrowser
		loadDriver();
		// InitializeTestData
		loadTestDataFromJson();
		// InitializeReports
		loadReports();
	}

	@AfterTest
	public void afterTestRun() {

		if (driver != null)
			driver.quit();

		report.endReport();
	}

	private void loadTestName(ITestContext context) {
		try {
			strTestName = context.getName();

			if (strTestName.equals("Default test")) {
				List<XmlClass> classes = context.getCurrentXmlTest().getClasses();

				String simpleName = classes.get(0).getName();
				strTestName = simpleName.substring(simpleName.lastIndexOf(".") + 1);

				// List<XmlInclude> methods = classes.get(0).getIncludedMethods();

				/*
				 * if (methods.isEmpty()) { String simpleName = classes.get(0).getName();
				 * strTestName = simpleName.substring(simpleName.lastIndexOf(".")+1); } else {
				 * strTestName = methods.get(0).getName(); }
				 */
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void loadProperties() {
		try {
			File file=new File(System.getProperty("user.dir")+"//src//main//resources//config//Config.properties");
			FileInputStream input = new FileInputStream(file);
			prop = new Properties();
			prop.load(input);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void loadDriver() {
		try {
			String browserName = prop.getProperty("Browser");
			String ip = prop.getProperty("IP");
			String port = prop.getProperty("Port");

			if (browserName.equalsIgnoreCase("CHROME")) {
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + File.separator + "src/main/resources/library" + File.separator + "chromedriver.exe");

				ChromeOptions options = new ChromeOptions();
				options.addArguments("--start-maximized");
				driver = new ChromeDriver(options);

			} else if (browserName.equalsIgnoreCase("REMOTE")) {
				// DesiredCapabilities capability = DesiredCapabilities.chrome();
				ChromeOptions options = new ChromeOptions();
				options.setExperimentalOption("w3c", false);
				options.setCapability(ChromeOptions.CAPABILITY, options);
				driver = new RemoteWebDriver(new URL("http://" + ip + ":" + port + "/wd/hub"), options);

			} else {
				System.out.println("Valid browser request is not submitted");

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void loadTestDataFromJson() {
		try {
			JSONParser jsonObj = new JSONParser();
			JSONObject json = (JSONObject) jsonObj.parse(new FileReader(System.getProperty("user.dir")+"//testData//InputData.json"));
			JSONObject testJSON = (JSONObject) json.get(strTestName);

			Set<String> keys = testJSON.keySet();
			testData = new HashMap<String, String>();

			for (String key : keys) {
				testData.put(key, (String) testJSON.get(key));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void loadReports() {
		try {
			report = new Reports(strTestName);
			report.setDriver(driver);
			report.setProp(prop);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
