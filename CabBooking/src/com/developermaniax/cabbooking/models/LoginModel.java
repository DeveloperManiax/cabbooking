package com.developermaniax.cabbooking.models;

public class LoginModel {

	String UserName;
	String Password;
	
	public LoginModel() {
		// TODO Auto-generated constructor stub
	}

	
	public LoginModel(String userName, String password) {
		super();
		UserName = userName;
		Password = password;
	}


	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}
	
	
}
