package com.availity.enrollments;

public class Enrollee implements java.io.Serializable {
	
	
	public String userID;
	public String fName;
	public String lName;
	public String vrsion;
	public String insuranceCompany;

	// set enrollee attributes
	public void setUserID(String userid) {
		userID = userid;		
	}

	public void setFirstName(String fname) {
		fName = fname;	
	}

	public void setLastName(String lname) {
		lName = lname;
	}

	public void setVersion(String version) {
		vrsion = version;
	}

	public void setInsurancecompany(String insuranceCo) {
		insuranceCompany = insuranceCo;
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
		return vrsion;
	}

	public String getInsuranceCompany() {
		return insuranceCompany;
	}
}
