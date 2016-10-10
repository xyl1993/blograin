package login.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import actical.model.ActicalModel;
import summer.base.util.BASE64Encoder;
import summer.base.util.ConstDefine;
import summer.core.constant.BusinessError;
import login.mapper.LoginMapper;
import login.model.UserInfo;
import login.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private LoginMapper loginMapper;
	protected static Logger logger = Logger.getLogger("login");// 异常信息
	public Map<String, Object> register(UserInfo userInfo) {
		Map<String, Object> respMap = new HashMap<String, Object>();
		try{
			int count = loginMapper.selectOne(userInfo.getEmail());
			if(count>0){
				respMap.put(ConstDefine.CONST_SUCCESS_KEY, false);
				respMap.put(ConstDefine.CONST_MESSAGE_KEY, "当前邮箱已经注册");
			}else{
				//生成token
				String token = BASE64Encoder.getToken(userInfo.getEmail());
				userInfo.setToken(token);
				userInfo.setHeadUrl("/PetForum//uploadFile//head.png");
				loginMapper.register(userInfo);
				respMap.put(ConstDefine.CONST_SUCCESS_KEY, true);
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			respMap.put(ConstDefine.CONST_SUCCESS_KEY, false);
			respMap.put(ConstDefine.CONST_MESSAGE_KEY, BusinessError.SERVELT_ERROR);
		}
		
		return respMap;
	}
	public Map<String, Object> getUserData(UserInfo userInfo) {
		Map<String,Object> respMap = new HashMap<String,Object>();
		try{
			List<UserInfo> dataList = loginMapper.getUserData(userInfo);
			if(dataList.size()>0){
				respMap.put(ConstDefine.CONST_SUCCESS_KEY, true);
				respMap.put(ConstDefine.CONST_DATA_KEY, dataList);
			}else{
				respMap.put(ConstDefine.CONST_SUCCESS_KEY, false);
				respMap.put(ConstDefine.CONST_MESSAGE_KEY, "用户名或密码错误");
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			respMap.put(ConstDefine.CONST_SUCCESS_KEY, false);
			respMap.put(ConstDefine.CONST_MESSAGE_KEY, BusinessError.SERVELT_ERROR);
		}
		
		return respMap;
	}
	public Map<String, Object> getUserDataDetail(int id) {
		Map<String,Object> respMap = new HashMap<String,Object>();
		try{
			UserInfo userInfo = loginMapper.getUserDataDetail(id);
			Integer sendNum = loginMapper.getSendNum(id);
			Integer replyNum = loginMapper.getReplyNum(id);
			respMap.put(ConstDefine.CONST_SUCCESS_KEY, true);
			respMap.put(ConstDefine.CONST_DATA_KEY,userInfo);
			respMap.put("sendNum",sendNum);
			respMap.put("replyNum",replyNum);
		}catch(Exception e){
			logger.error(e.getMessage());
			respMap.put(ConstDefine.CONST_SUCCESS_KEY, false);
			respMap.put(ConstDefine.CONST_MESSAGE_KEY, BusinessError.SERVELT_ERROR);
		}
		return respMap;
	}
	public Map<String, Object> updatePic(UserInfo userInfo) {
		Map<String, Object> respMap = new HashMap<String, Object>();
		try{
			loginMapper.updatePic(userInfo);
			respMap.put(ConstDefine.CONST_SUCCESS_KEY, true);
		}catch(Exception e){
			logger.error(e.getMessage());
			respMap.put(ConstDefine.CONST_SUCCESS_KEY, false);
			respMap.put(ConstDefine.CONST_MESSAGE_KEY, BusinessError.SERVELT_ERROR);
		}
		
		return respMap;
	}
	public Map<String, Object> login(UserInfo userInfo) {
		Map<String,Object> respMap = new HashMap<String, Object>();
		UserInfo user = loginMapper.selectByLogin(userInfo);
		if(user == null){
			respMap.put(ConstDefine.CONST_SUCCESS_KEY, false);
			respMap.put(ConstDefine.CONST_MESSAGE_KEY, "用户名或密码错误");
		}else{
			Date lastLoginTime = user.getLastLoginTime();
			Calendar calendarCurrent = Calendar.getInstance();
			// 初始化 Calendar 对象，但并不必要，除非需要重置时间
			calendarCurrent.setTime(new Date());
			if (lastLoginTime != null) {
				// 创建 发送验证码时间 Calendar 对象
				Calendar calendarLogin = Calendar.getInstance();
				calendarLogin.setTime(lastLoginTime);
				// 判断验证使用过或者验证码时间超过（默认10天）
				calendarLogin.add(Calendar.DAY_OF_MONTH, 10);
				if (calendarCurrent.compareTo(calendarLogin) != -1) {
					String token = BASE64Encoder.getToken(user.getEmail());
					user.setToken(token);
				}
			} else {
				String token = BASE64Encoder.getToken(user.getEmail());
				user.setToken(token);
			}
			user.setLoginStatus(1);
			loginMapper.updateByLogin(user);
			respMap.put(ConstDefine.CONST_SUCCESS_KEY, true);
			respMap.put(ConstDefine.CONST_DATA_KEY, user);
		}
		return respMap;
	}
	
	public void userLogout(String token) {
		loginMapper.userLogout(token);
	}
	public Map<String, Object> getUserDetail(String token) {
		Map<String, Object> respMap = new HashMap<String, Object>();
		try{
			UserInfo userInfo = loginMapper.getUserDetail(token);
			int userId = userInfo.getId();
			Integer sendNum = loginMapper.getSendNum(userId);
			Integer replyNum = loginMapper.getReplyNum(userId);
			Integer infoNum = loginMapper.getInfoNum(userId);
			respMap.put("sendNum",sendNum);
			respMap.put("replyNum",replyNum);
			respMap.put("infoNum",infoNum);
			respMap.put(ConstDefine.CONST_SUCCESS_KEY, true);
			respMap.put(ConstDefine.CONST_DATA_KEY,userInfo);
		}catch(Exception e){
			logger.error(e.getMessage());
			respMap.put(ConstDefine.CONST_SUCCESS_KEY, false);
			respMap.put(ConstDefine.CONST_MESSAGE_KEY, BusinessError.SERVELT_ERROR);
		}
		return respMap;
	}
	public Map<String, Object> updateUser(UserInfo userInfo, String token) {
		Map<String, Object> respMap = new HashMap<String, Object>();
		userInfo.setToken(token);
		try{
			loginMapper.updateUser(userInfo);
			respMap.put(ConstDefine.CONST_SUCCESS_KEY, true);
		}catch(Exception e){
			logger.error(e.getMessage());
			respMap.put(ConstDefine.CONST_SUCCESS_KEY, false);
			respMap.put(ConstDefine.CONST_MESSAGE_KEY, BusinessError.SERVELT_ERROR);
		}
		return respMap;
	}
	public Map<String, Object> updatePassWord(UserInfo userInfo, String token) {
		Map<String, Object> respMap = new HashMap<String, Object>();
		userInfo.setToken(token);
		try{
			if(loginMapper.selectOneByTokenAndPassword(userInfo)==0){
				respMap.put(ConstDefine.CONST_SUCCESS_KEY, false);
				respMap.put(ConstDefine.CONST_MESSAGE_KEY, "原密码不正确");
			}else{
				loginMapper.updatePassWord(userInfo);
				respMap.put(ConstDefine.CONST_SUCCESS_KEY, true);
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			respMap.put(ConstDefine.CONST_SUCCESS_KEY, false);
			respMap.put(ConstDefine.CONST_MESSAGE_KEY, BusinessError.SERVELT_ERROR);
		}
		return respMap;
	}
	
}
