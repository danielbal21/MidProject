package Entities;

import java.io.Serializable;

public class NotificationInTable implements Serializable {

	private int notificationnumber;
	private String content;
	private String status;
	private String from;
	
	public NotificationInTable(int notificationnumber,String from, String content, String status) {
		this.notificationnumber = notificationnumber;
		this.content = content;
		this.status = status;
		this.from = from;
	}
	
	public int getNotificationnumber() {
		return notificationnumber;
	}
	public void setNotificationnumber(int notificationnumber) {
		this.notificationnumber = notificationnumber;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	
	@Override
	public String toString() {
		return "NotificationInTable [notificationnumber=" + notificationnumber  + ", content="
			+ content + ", status=" + status + ", from=" + from + "]";
	}
}
