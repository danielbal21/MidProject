package Entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import Utilities.GenericUtilties;

// TODO: Auto-generated Javadoc
/**
 * contain Complaint info to transfer between the server and the client.
 */
public class Complaint implements Serializable{
	
	/** The complainant id in the database */
	private String user_id;
	
	/** The complaint id in the database. */
	private int complaint_id;
	
	/** The complain text. */
	private String complain_text;
	
	/** Customer service response */
	private String answer_text;
	
	/** The time that the complain been posted. */
	private Timestamp complain_time;
	
	/** The cost. */
	private int cost;
	
	/** The refund that granted. */
	private int refund;
	
	/** The name of the branch to which the complaint is associated */
	private String branch;
	
	/** ID number of the complainant . */
	private String IDnumber;
	
	/**  string of the date in format "dd/MM/yyyy HH:mm:ss".
	 * is set when  complain_time is set for GUI purpose */
	private String Comp_date;
	
	/** The is not notified. */
	private int isNotNotified;
	
	/**
	 * Gets the checks if is not notified.
	 *
	 * @return the checks if is not notified
	 */
	public int getIsNotNotified() {
		return isNotNotified;
	}
	
	/**
	 * Sets the checks if is not notified.
	 *
	 * @param isNotNotified the new checks if is not notified
	 */
	public void setIsNotNotified(int isNotNotified) {
		this.isNotNotified = isNotNotified;
	}
	
	/**
	 * Gets the comp date.
	 *
	 * @return the comp date
	 */
	public String getComp_date() {
		return Comp_date;
	}	
	
	/**
	 * Gets the i dnumber.
	 *
	 * @return the i dnumber
	 */
	public String getIDnumber() {
		return IDnumber;
	}
	
	/**
	 * Sets the i dnumber.
	 *
	 * @param iDnumber the new i dnumber
	 */
	public void setIDnumber(String iDnumber) {
		IDnumber = iDnumber;
	}
	
	/**
	 * Gets the branch.
	 *
	 * @return the branch
	 */
	public String getBranch() {
		return branch;
	}
	
	/**
	 * Sets the branch.
	 *
	 * @param branch the new branch
	 */
	public void setBranch(String branch) {
		this.branch = branch;
	}
	
	
	
	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public String getUser_id() {
		return user_id;
	}
	
	/**
	 * Sets the user id.
	 *
	 * @param user_id the new user id
	 */
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	
	/**
	 * Gets the complain text.
	 *
	 * @return the complain text
	 */
	public String getComplain_text() {
		return complain_text;
	}
	
	/**
	 * Sets the complain text.
	 *
	 * @param complain_text the new complain text
	 */
	public void setComplain_text(String complain_text) {
		this.complain_text = complain_text;
	}
	
	/**
	 * Gets the answer text.
	 *
	 * @return the answer text
	 */
	public String getAnswer_text() {
		return answer_text;
	}
	
	/**
	 * Sets the answer text.
	 *
	 * @param answer_text the new answer text
	 */
	public void setAnswer_text(String answer_text) {
		this.answer_text = answer_text;
	}
	
	/**
	 * Gets the complain time.
	 *
	 * @return the complain time
	 */
	public Timestamp getComplain_time() {
		return complain_time;
	}
	
	/**
	 * Sets the complain time.
	 *
	 * @param complain_time the new complain time
	 */
	public void setComplain_time(Timestamp complain_time) {
		this.complain_time = complain_time;
		this.Comp_date=DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(GenericUtilties.Convert_LocalDate_To_SQLDate(complain_time));
	}
	
	/**
	 * Gets the cost.
	 *
	 * @return the cost
	 */
	public int getCost() {
		return cost;
	}
	
	/**
	 * Sets the cost.
	 *
	 * @param cost the new cost
	 */
	public void setCost(int cost) {
		this.cost = cost;
	}
	
	/**
	 * Gets the refund.
	 *
	 * @return the refund
	 */
	public int getRefund() {
		return refund;
	}
	
	/**
	 * Sets the refund.
	 *
	 * @param refund the new refund
	 */
	public void setRefund(int refund) {
		this.refund = refund;
	}
	
	/**
	 * Gets the complaint id.
	 *
	 * @return the complaint id
	 */
	public int getComplaint_id() {
		return complaint_id;
	}
	
	/**
	 * Sets the complaint id.
	 *
	 * @param complaint_id the new complaint id
	 */
	public void setComplaint_id(int complaint_id) {
		this.complaint_id = complaint_id;
	}
	
	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Complaint [user_id=" + user_id + ", complaint_id=" + complaint_id + ", complain_text=" + complain_text
				+ ", answer_text=" + answer_text + ", complain_time=" + complain_time + ", cost=" + cost + ", branch="
				+ branch + ", refund=" + refund + "]";
	}
}
