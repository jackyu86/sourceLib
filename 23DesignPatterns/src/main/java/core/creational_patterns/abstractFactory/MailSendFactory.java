package core.creational_patterns.abstractFactory;

public class MailSendFactory implements Provider {

	public Sender produce() {
		return new MailSender();
	}


}
