package core.creational_patterns.abstractFactory;

public class SmsSender implements Sender {

	public void send() {
		System.out.println("---------->SmsSender.Send()");
	}

}
