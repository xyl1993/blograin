package mails.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import mails.model.MailModel;

@Service
public interface MailService {

	public Map<String,Object> sendMail(MailModel mailModel);
}
