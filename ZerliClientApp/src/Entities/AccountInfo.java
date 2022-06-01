package Entities;

import java.io.Serializable;

import Utilities.GenericUtilties;

public class AccountInfo implements Serializable{

	private String userID;
	private String firstName;
	private String lastName;
	private String ID;
	private Roles role;
	private Access access;
	
	public AccountInfo() {}

	public AccountInfo(String userID, String firstName, String lastName, String iD) {
		super();
		this.userID = userID;
		this.firstName = firstName;
		this.lastName = lastName;
		ID = iD;
	}

	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}

	public Roles getRole() {
		return role;
	}

	public void setRole(Roles role) {
		this.role = role;
	}

	public Access getAccess() {
		return access;
	}

	public void setAccess(Access access) {
		this.access = access;
	}

}