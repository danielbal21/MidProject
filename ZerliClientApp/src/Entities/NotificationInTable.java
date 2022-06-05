package Entities;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class NotificationInTable.
 */
public class NotificationInTable implements Serializable {

	/** The notificationnumber. */
	private int notificationnumber;
	
	/** The content. */
	private String content;
	
	/** The status. */
	private String status;
	
	/** The from. */
	private String from;
	
	/** The to. */
	private String to;
	
	/**
	 * Instantiates a new notification in table.
	 */
	public NotificationInTable() {}

	/**
	 * Instantiates a new notification in table.
	 *
	 * @param notificationnumber the notificationnumber
	 * @param from the from
	 * @param content the content
	 * @param status the status
	 */
	public NotificationInTable(int notificationnumber,String from, String content, String status) {
		this.notificationnumber = notificationnumber;
		this.content = content;
		this.status = status;
		this.from = from;
	}
	
	/**
	 * Gets the to.
	 *
	 * @return the to
	 */
	public String getTo() {
		return to;
	}

	/**
	 * Sets the to.
	 *
	 * @param to the new to
	 */
	public void setTo(String to) {
		this.to = to;
	}

	/**
	 * Gets the notificationnumber.
	 *
	 * @return the notificationnumber
	 */
	public int getNotificationnumber() {
		return notificationnumber;
	}
	
	/**
	 * Sets the notificationnumber.
	 *
	 * @param notificationnumber the new notificationnumber
	 */
	public void setNotificationnumber(int notificationnumber) {
		this.notificationnumber = notificationnumber;
	}
	
	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	
	/**
	 * Sets the content.
	 *
	 * @param content the new content
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * Gets the from.
	 *
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}
	
	/**
	 * Sets the from.
	 *
	 * @param from the new from
	 */
	public void setFrom(String from) {
		this.from = from;
	}
	
	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "NotificationInTable [notificationnumber=" + notificationnumber  + ", content="
			+ content + ", status=" + status + ", from=" + from + "]";
	}
}
