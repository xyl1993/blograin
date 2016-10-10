package actical.model;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import summer.base.util.CustomDateSerializer;
import login.model.UserInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * 帖子实体类
 * @author bxw
 *
 */
@JsonInclude(Include.NON_NULL)
public class ActicalModel implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer userId;
	private String title;
	private String content;
	private String abstext;
	private Integer replyNum;
	private Integer collectionNum;
	private Integer attentionNum;
	private UserInfo userInfo;
	private String titleImg;
	private String userName;
	private Date createTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getReplyNum() {
		return replyNum;
	}
	public void setReplyNum(Integer replyNum) {
		this.replyNum = replyNum;
	}
	public Integer getCollectionNum() {
		return collectionNum;
	}
	public void setCollectionNum(Integer collectionNum) {
		this.collectionNum = collectionNum;
	}
	public Integer getAttentionNum() {
		return attentionNum;
	}
	public void setAttentionNum(Integer attentionNum) {
		this.attentionNum = attentionNum;
	}
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getAbstext() {
		return abstext;
	}
	public void setAbstext(String abstext) {
		this.abstext = abstext;
	}
	public String getTitleImg() {
		return titleImg;
	}
	public void setTitleImg(String titleImg) {
		this.titleImg = titleImg;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}
