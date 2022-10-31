package com.availity.enrollments;

public class Enrollee {
	
	
	private String userID;
	private String fName;
	private String lName;
	private String version;
	private String insuranceCompany;

	// set enrollee attributes
	public void setUserID(String userID) {
		userID = this.userID;		
	}

	public void setFirstName(String fName) {
		fName = this.fName;	
	}

	public void setLastName(String lName) {
		lName = this.lName;
	}

	public void setVersion(String string) {
		string = this.version;
	}

	public void setInsurancecompany(String insuranceCompany) {
		insuranceCompany = this.insuranceCompany;
	}

	// get enrollee attributes
	public String getUserID() {
		return userID;
	}

	public String getFirstName() {
		return fName;
	}

	public String getLastName() {
		return lName;
	}

	public String getVersion() {
		return version;
	}

	public String getInsuranceCompany() {
		return insuranceCompany;
	}
}
