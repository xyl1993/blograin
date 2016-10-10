package actical.model;

import java.util.Date;


import login.model.UserInfo;

/**
 * 用户评论model
 * @author xyl
 *
 */
public class PlModel {

	private int id;
	
	private String userName;
	
	private Integer userId;
	
	private Integer invitationId;
	
	private String title;
	
	private String email;
	
	private String content;
	
	private UserInfo userInfo;
	
	private Date replyTime;
	
	private String replayName;        //被回复人姓名
	
	private String replayContent;     //被回复人的评论内容
	
	private String headUrl;
	
	private Integer replayId;
	
	private int replayUserId;
	
	private int fpUserId;
	
	private Integer replayToId;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getInvitationId() {
		return invitationId;
	}

	public void setInvitationId(Integer invitationId) {
		this.invitationId = invitationId;
	}

	public Date getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}

	public String getReplayName() {
		return replayName;
	}

	public void setReplayName(String replayName) {
		this.replayName = replayName;
	}

	public String getReplayContent() {
		return replayContent;
	}

	public void setReplayContent(String replayContent) {
		this.replayContent = replayContent;
	}

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public Integer getReplayId() {
		return replayId;
	}

	public void setReplayId(Integer replayId) {
		this.replayId = replayId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getReplayUserId() {
		return replayUserId;
	}

	public void setReplayUserId(Integer replayUserId) {
		this.replayUserId = replayUserId;
	}

	public Integer getFpUserId() {
		return fpUserId;
	}

	public void setFpUserId(Integer fpUserId) {
		this.fpUserId = fpUserId;
	}

	public Integer getReplayToId() {
		return replayToId;
	}

	public void setReplayToId(Integer replayToId) {
		this.replayToId = replayToId;
	}
	
}
