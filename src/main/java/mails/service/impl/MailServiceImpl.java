package mails.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import summer.base.util.ConstDefine;
import summer.base.util.SendmailUtil;
import summer.core.constant.BusinessError;
import mails.mapper.MailMapper;
import mails.model.MailModel;
import mails.service.MailService;

@Service
public class MailServiceImpl implements MailService {

	protected static Logger logger = Logger.getLogger("login");// 异常信息
	
	@Autowired
	private MailMapper mailMapper;
	
	public Map<String,Object> sendMail(MailModel mailModel) {

		Map<String,Object> respMap = new HashMap<String, Object>();
		try{
			mailMapper.sendMail(mailModel);
			//发送邮件给我自己
			SendmailUtil se = new SendmailUtil();
	        String text = "标题:"+mailModel.getTitle()+"</br>";
	        text = text+"发件人:"+mailModel.getEmail()+"</br>";
	        text = text + "内容:"+mailModel.getText();
	        se.doSendHtmlEmail("意见反馈", text, "602165057@qq.com");
			respMap.put(ConstDefine.CONST_SUCCESS_KEY, true);
		}catch(Exception e){
			logger.error(e.getMessage());
			respMap.put(ConstDefine.CONST_SUCCESS_KEY, false);
			respMap.put(ConstDefine.CONST_MESSAGE_KEY, BusinessError.SERVELT_ERROR);
		}
		return respMap;
	}

}
