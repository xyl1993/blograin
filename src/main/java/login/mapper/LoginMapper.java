package login.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import login.model.UserInfo;

public interface LoginMapper {

	public void register(UserInfo userInfo);
	
	public List<UserInfo> getUserData(UserInfo userInfo);
	
	public UserInfo selectByLogin(UserInfo userInfo);
	
	public void updateByLogin(UserInfo userInfo);
	
	public int selectOne(@Param("email") String email);
	
	public int getSendNum(int id);
	public int getReplyNum(int id);
	public int getInfoNum(int userId);
	public UserInfo getUserDataDetail(int id);
	
	public void updatePic(UserInfo userInfo);
	
	public void userLogout(String token);
	
	public UserInfo getUserDetail(String token);
	
	public int getUserIdByToken(String token);
	
	public void updateUser(UserInfo userInfo);
	
	public int selectOneByTokenAndPassword(UserInfo userInfo);
	
	public void updatePassWord(UserInfo userInfo);
}
