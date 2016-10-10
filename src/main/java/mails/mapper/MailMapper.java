package mails.mapper;

import mails.model.MailModel;

public interface MailMapper {

	public void sendMail(MailModel mailModel);
}
