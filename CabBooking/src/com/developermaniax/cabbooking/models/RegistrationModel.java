package com.developermaniax.cabbooking.models;

public class RegistrationModel {

	String UserId;
	String FName;
	String LName;
	String Address;
	String MobileNo;
	String Password;
	String EmailId;
	String City;
	
	
	public RegistrationModel() {
	}
	public RegistrationModel(String fName, String lName, String mobileNo,
			String password, String emailId,String city) {
		super();
		FName = fName;
		LName = lName;
		MobileNo = mobileNo;
		Password = password;
		EmailId = emailId;
		City = city;
	}
	public String getUserId() {
		return UserId;
	}
	public void setUserId(String userId) {
		UserId = userId;
	}
	public String getFName() {
		return FName;
	}
	public void setFName(String fName) {
		FName = fName;
	}
	public String getLName() {
		return LName;
	}
	public void setLName(String lName) {
		LName = lName;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getMobileNo() {
		return MobileNo;
	}
	public void setMobileNo(String mobileNo) {
		MobileNo = mobileNo;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getEmailId() {
		return EmailId;
	}
	public void setEmailId(String emailId) {
		EmailId = emailId;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	
	
	
	
}
