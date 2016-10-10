package mails.model;

import java.util.Date;

/**
 * 发送邮件model
 * @author xyl
 *
 */
public class MailModel implements java.io.Serializable {

	private static final long serialVersionUID = -8324933449652985542L;

	private int id;
	
	private String text;
	
	private String email;
	
	private Date create_time;
	
	private String title;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
