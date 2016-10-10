package mails.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mails.model.MailModel;
import mails.service.MailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("mails")
public class MailController {

	@Autowired
	private MailService mailService;
	
	@RequestMapping(value = "/sendMail",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object>sendMail(@RequestBody MailModel model, HttpServletRequest request,HttpServletResponse response){
		return mailService.sendMail(model);
	} 
}
