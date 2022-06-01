package Entities;

import java.io.Serializable;

import Utilities.GenericUtilties;

// TODO: Auto-generated Javadoc
/**
 * The Class AccountInfo.
 */
public class AccountInfo implements Serializable{

	/** The user ID. */
	private String userID;
	
	/** The first name. */
	private String firstName;
	
	/** The last name. */
	private String lastName;
	
	/** The id. */
	private String ID;
	
	/** The role. */
	private Roles role;
	
	/** The access. */
	private Access access;
	
	/**
	 * Instantiates a new account info.
	 */
	public AccountInfo() {}

	/**
	 * Instantiates a new account info.
	 *
	 * @param userID the user ID
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param iD the i D
	 */
	public AccountInfo(String userID, String firstName, String lastName, String iD) {
		super();
		this.userID = userID;
		this.firstName = firstName;
		this.lastName = lastName;
		ID = iD;
	}
	
	/**
	 * Gets the user ID.
	 *
	 * @return the user ID
	 */
	public String getUserID() {
		return userID;
	}
	
	/**
	 * Sets the user ID.
	 *
	 * @param userID the new user ID
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getID() {
		return ID;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param iD the new id
	 */
	public void setID(String iD) {
		ID = iD;
	}

	/**
	 * Gets the role.
	 *
	 * @return the role
	 */
	public Roles getRole() {
		return role;
	}

	/**
	 * Sets the role.
	 *
	 * @param role the new role
	 */
	public void setRole(Roles role) {
		this.role = role;
	}

	/**
	 * Gets the access.
	 *
	 * @return the access
	 */
	public Access getAccess() {
		return access;
	}

	/**
	 * Sets the access.
	 *
	 * @param access the new access
	 */
	public void setAccess(Access access) {
		this.access = access;
	}

}