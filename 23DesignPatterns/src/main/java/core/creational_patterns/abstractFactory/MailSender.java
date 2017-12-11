package core.creational_patterns.abstractFactory;

public class MailSender implements Sender {

	public void send() {
		System.out.println("---------->MailSender.Send()");
	}

}
