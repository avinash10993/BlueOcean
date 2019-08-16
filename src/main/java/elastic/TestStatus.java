package elastic;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TestStatus {
	@JsonProperty("testClass")
	private String testClass;

	@JsonProperty("description")
	private String description;

	@JsonProperty("status")
	private String status;

	@JsonProperty("executionTime")
	private String executionTime;

	@JsonProperty("executionDate")
	private String executionDate;
	
	
	public String getExecutionDate() {
		return executionDate;
	}

	public void setExecutionDate(String executionDate) {
		this.executionDate = executionDate;
	}

	public String getTestClass() {
		return testClass;
	}

	public void setTestClass(String testClass) {
		this.testClass = testClass;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(String executionTime) {
		this.executionTime = executionTime;
	}

}
