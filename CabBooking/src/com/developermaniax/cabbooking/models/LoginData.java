package com.developermaniax.cabbooking.models;

public class LoginData {

	static String UserId;
	static String fName;
	static String lName;
	static String emailId;
	static String mobileNo;	
	static String address;
	static String cityId;
	static String password;
	static String date;

	public LoginData() {
		// TODO Auto-generated constructor stub
	}

	public static String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}

	public static String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public static String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public static String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public static String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public static String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public static String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
