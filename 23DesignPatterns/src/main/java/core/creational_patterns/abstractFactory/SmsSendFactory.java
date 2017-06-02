package core.creational_patterns.abstractFactory;

public class SmsSendFactory implements Provider {

	public Sender produce() {
		return new SmsSender();
	}
}
