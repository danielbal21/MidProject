package Entities;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class CustomerInfo.
 */
public class CustomerInfo implements Serializable{
	
	/** The first name. */
	private String firstName;
	
	/** The last name. */
	private String lastName;
	
	/** The id. */
	private String ID;
	
	/** The email. */
	private String email;
	
	/** The phone. */
	private String phone;
	
	/** The credit card. */
	private String creditCard;
	
	/** The cvv. */
	private String cvv;
	
	/** The exp month. */
	private String expMonth;
	
	/** The exp year. */
	private String expYear;
	
	/** The zerli coins. */
	private String zerliCoins;
	
	/** The new customer. */
	private boolean newCustomer;
	
	/**
	 * Instantiates a new customer info.
	 */
	public CustomerInfo() {}

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
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the phone.
	 *
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Sets the phone.
	 *
	 * @param phone the new phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Gets the credit card.
	 *
	 * @return the credit card
	 */
	public String getCreditCard() {
		return creditCard;
	}

	/**
	 * Sets the credit card.
	 *
	 * @param creditCard the new credit card
	 */
	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}

	/**
	 * Gets the cvv.
	 *
	 * @return the cvv
	 */
	public String getCvv() {
		return cvv;
	}

	/**
	 * Sets the cvv.
	 *
	 * @param cvv the new cvv
	 */
	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	/**
	 * Gets the exp month.
	 *
	 * @return the exp month
	 */
	public String getExpMonth() {
		return expMonth;
	}

	/**
	 * Sets the exp month.
	 *
	 * @param expMonth the new exp month
	 */
	public void setExpMonth(String expMonth) {
		this.expMonth = expMonth;
	}

	/**
	 * Gets the exp year.
	 *
	 * @return the exp year
	 */
	public String getExpYear() {
		return expYear;
	}

	/**
	 * Sets the exp year.
	 *
	 * @param expYear the new exp year
	 */
	public void setExpYear(String expYear) {
		this.expYear = expYear;
	}

	/**
	 * Gets the zerli coins.
	 *
	 * @return the zerli coins
	 */
	public String getZerliCoins() {
		return zerliCoins;
	}

	/**
	 * Sets the zerli coins.
	 *
	 * @param zerliCoins the new zerli coins
	 */
	public void setZerliCoins(String zerliCoins) {
		this.zerliCoins = zerliCoins;
	}

	/**
	 * Checks if is new customer.
	 *
	 * @return true, if is new customer
	 */
	public boolean isNewCustomer() {
		return newCustomer;
	}

	/**
	 * Sets the new customer.
	 *
	 * @param newCustomer the new new customer
	 */
	public void setNewCustomer(boolean newCustomer) {
		this.newCustomer = newCustomer;
	}
	
}
