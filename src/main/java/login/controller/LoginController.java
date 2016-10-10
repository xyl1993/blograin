package login.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import login.model.UserInfo;
import login.service.LoginService;
import mails.model.MailModel;
import mails.service.MailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import summer.base.util.ConstDefine;
import summer.core.constant.BusinessError;



@Controller
@RequestMapping("login")
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	@RequestMapping(value = "/register",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object>register(@RequestBody UserInfo model, HttpServletRequest request,HttpServletResponse response){
		return loginService.register(model);
	}
	
	@RequestMapping(value = "/getUserData",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object>getUserData(@RequestBody UserInfo model, HttpServletRequest request,HttpServletResponse response){
		return loginService.getUserData(model);
	}
	
	@RequestMapping(value = "/doLogin",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object>login(@RequestBody UserInfo model, HttpServletRequest request,HttpServletResponse response){
		return loginService.login(model);
	}
	
	@RequestMapping(value="/getUserDataDetail",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object>getUserDataDetail(int id, HttpServletRequest request,HttpServletResponse response){
		return loginService.getUserDataDetail(id);
	}
	
	@RequestMapping(value = "/updatePic",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object>updatePic(@RequestBody UserInfo model, HttpServletRequest request,HttpServletResponse response){
		return loginService.updatePic(model);
	}
	
	@RequestMapping(value = "/userLogout" ,method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object>userLogout(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> respMap = new HashMap<String, Object>();
		String token = request.getHeader("token");
		try {
			loginService.userLogout(token);
			respMap.put(ConstDefine.CONST_SUCCESS_KEY, true);
		} catch (Exception e) {
			respMap.put(ConstDefine.CONST_SUCCESS_KEY, false);
			respMap.put(ConstDefine.CONST_MESSAGE_KEY, BusinessError.SERVELT_ERROR);
		}
		return respMap;
	}
	
	@RequestMapping(value = "/getUserDetail",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object>getUserDetail(HttpServletRequest request,HttpServletResponse response){
		return loginService.getUserDetail(request.getHeader("token"));
	}
	
	/**
	 * 用户修改信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object>updateUser(@RequestBody UserInfo model ,HttpServletRequest request,HttpServletResponse response){
		return loginService.updateUser(model,request.getHeader("token"));
	}
	
	/**
	 * 用户密码
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/updatePassWord", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updatePassWord(@RequestBody UserInfo model ,HttpServletRequest request,HttpServletResponse response){
		return loginService.updatePassWord(model,request.getHeader("token"));
	}
}
