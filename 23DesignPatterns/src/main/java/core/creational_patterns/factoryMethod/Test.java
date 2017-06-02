package core.creational_patterns.factoryMethod;

/**
 * @ClassName: Test
 * @Description: 普通工厂模式：
 * 总体来说，工厂模式适合：
 * 凡是出现了大量的产品需要创建，并且具有共同的接口时，可以通过工厂方法模式进行创建。
 * 所以，大多数情况下，我们会选用静态工厂方法模式。
 * @author qinf QQ:908247035
 * @date 2017年2月15日
 * @version V1.0
 */
public class Test {

	public static void main(String[] args) {
		Sender sender = SendFactory.produce("mail");
		
		sender.send();
		
		sender = SendFactory.produce("sms");
		
		sender.send();
	}
}
