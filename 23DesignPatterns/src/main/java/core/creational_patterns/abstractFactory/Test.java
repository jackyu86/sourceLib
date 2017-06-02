package core.creational_patterns.abstractFactory;

/**
 * @ClassName: Test
 * @Description: 抽象工厂模式：
 * 其实这个模式的好处就是，如果你现在想增加一个功能：
 * 发及时信息，则只需做一个实现类，实现Sender接口，同时做一个工厂类，
 * 实现Provider接口，就OK了，无需去改动现成的代码。这样做，拓展性较好！
 * @author qinf QQ:908247035
 * @date 2017年2月15日
 * @version V1.0
 */
public class Test {

	public static void main(String[] args) {
		Provider provider = new MailSendFactory();
		
		Sender sender = provider.produce();
		
		sender.send();
		
		provider = new SmsSendFactory();
		
		sender = provider.produce();
		
		sender.send();
	}
}
