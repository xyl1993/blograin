package login.service;

import java.util.Map;

import login.model.UserInfo;

public interface LoginService {

	public Map<String,Object> register(UserInfo userInfo);
	
	public Map<String,Object> getUserData(UserInfo userInfo);
	
	public Map<String,Object> getUserDataDetail(int id);
	
	public Map<String,Object> updatePic(UserInfo userInfo);
	
	public Map<String,Object> login(UserInfo userInfo);
	
	public void userLogout(String token);
	
	public Map<String,Object> getUserDetail(String token);
	
	public Map<String,Object> updateUser(UserInfo userInfo,String token);
	
	public Map<String,Object> updatePassWord(UserInfo userInfo,String token);
}
