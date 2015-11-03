package com.developermaniax.cabbooking.models;

public class EmergencyContact {

	String Name;
	String ContactNo;

	public EmergencyContact() {
		// TODO Auto-generated constructor stub
	}

	public EmergencyContact(String name, String contactNo) {
		Name = name;
		ContactNo = contactNo;
	}
	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getContactNo() {
		return ContactNo;
	}

	public void setContactNo(String contactNo) {
		ContactNo = contactNo;
	}

}
