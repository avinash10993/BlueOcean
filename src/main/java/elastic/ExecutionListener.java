package elastic;

import java.util.Calendar;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;



public class ExecutionListener implements ITestListener {

	private TestStatus testStatus;
	//private ApplicationDB appDB;
	Calendar calendar = Calendar.getInstance();
	public static Map<String,String> value;
	public void onFinish(ITestContext arg0) {

	}

	public void onStart(ITestContext context) {
		value=context.getCurrentXmlTest().getAllParameters();
		}

	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {

	}

	public void onTestFailure(ITestResult iTestResult) {

		this.sendStatus(iTestResult, "FAIL");
	}

	public void onTestSkipped(ITestResult iTestResult) {

		System.out.println("My deviceUDID " + iTestResult.getTestContext().getAttribute("name"));
		this.sendStatus(iTestResult, "SKIPPED");

	}

	private void sendStatus(ITestResult iTestResult, String status) {

		try {
//			String s1=iTestResult.getMethod().getTestClass().getName();
//			for(String keys:value.keySet())
//			{
//				if(keys.equalsIgnoreCase(s1))
//				System.out.println(value.get(keys));
//			}
//			
					
			this.testStatus.setDescription(iTestResult.getMethod().getDescription());
			this.testStatus.setStatus(status);
			this.testStatus.setExecutionTime(String.valueOf((float)((iTestResult.getEndMillis()-iTestResult.getStartMillis())/1000)) + " sec");
			this.testStatus.setTestClass(iTestResult.getTestClass().getName());
			this.testStatus.setExecutionDate(
					String.valueOf(calendar.get(Calendar.DATE)) + "/" + String.valueOf(calendar.get(Calendar.MONTH) + 1)
							+ "/" + String.valueOf(calendar.get(Calendar.YEAR)));
			ResultSender.send(this.testStatus);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void onTestStart(ITestResult iTestResult) {

		this.testStatus = new TestStatus();

	}

	public void onTestSuccess(ITestResult iTestResult) {

		this.sendStatus(iTestResult, "PASS");
	}

}
