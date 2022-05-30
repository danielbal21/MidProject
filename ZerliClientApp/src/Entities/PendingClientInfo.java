package Entities;

import java.io.Serializable;
import java.util.Arrays;

public class PendingClientInfo implements Serializable{
	
	private String UserID;
	private String ID;
	private String firstName;
	private String LastName;
	private String email;
	private String phone;
	private String[] creditPhrases;
	private String CVV;
	private String expirationMonth;
	private String expirationYear;
	private String password;
		
	public PendingClientInfo() {}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserID() {
		return UserID;
	}

	public void setUserID(String userID) {
		UserID = userID;
	}

	public String[] getArrayCreditPhrases() {
		return creditPhrases;
	}
	
	public String getStringCreditPhrases() {
		return creditPhrases[0]+creditPhrases[1]+creditPhrases[2]+creditPhrases[3];
	}
	
	public void setCreditPhrases(String[] creditPhrases) {
		this.creditPhrases = creditPhrases;
	}
	public String getCVV() {
		return CVV;
	}
	public void setCVV(String cVV) {
		CVV = cVV;
	}
	public String getExpirationMonth() {
		return expirationMonth;
	}
	public void setExpirationMonth(String expirationMonth) {
		this.expirationMonth = expirationMonth;
	}
	public String getExpirationYear() {
		return expirationYear;
	}
	public void setExpirationYear(String expirationYear) {
		this.expirationYear = expirationYear;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "PendingClientInfo [UserID=" + UserID + ", ID=" + ID + ", firstName=" + firstName + ", LastName="
				+ LastName + ", email=" + email + ", phone=" + phone + ", creditPhrases="
				+ Arrays.toString(creditPhrases) + ", CVV=" + CVV + ", expirationMonth=" + expirationMonth
				+ ", expirationYear=" + expirationYear + ", password=" + password + "]";
	}

}
