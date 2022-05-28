package Entities;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Formatter;

public class Complaint implements Serializable{
	private String user_id;
	private int complaint_id;
	private String complain_text;
	private String answer_text;
	private Timestamp complain_time;
	private int cost;
	private String branch;
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	private int refund;
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	
	public String getComplain_text() {
		return complain_text;
	}
	public void setComplain_text(String complain_text) {
		this.complain_text = complain_text;
	}
	public String getAnswer_text() {
		return answer_text;
	}
	public void setAnswer_text(String answer_text) {
		this.answer_text = answer_text;
	}
	public Timestamp getComplain_time() {
		return complain_time;
	}
	public void setComplain_time(Timestamp complain_time) {
		this.complain_time = complain_time;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public int getRefund() {
		return refund;
	}
	public void setRefund(int refund) {
		this.refund = refund;
	}
	public int getComplaint_id() {
		return complaint_id;
	}
	public void setComplaint_id(int complaint_id) {
		this.complaint_id = complaint_id;
	}
	@Override
	public String toString() {
		return "Complaint [user_id=" + user_id + ", complaint_id=" + complaint_id + ", complain_text=" + complain_text
				+ ", answer_text=" + answer_text + ", complain_time=" + complain_time + ", cost=" + cost + ", branch="
				+ branch + ", refund=" + refund + "]";
	}
	
	
	
	
}
