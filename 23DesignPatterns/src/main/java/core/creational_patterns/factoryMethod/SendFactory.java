package core.creational_patterns.factoryMethod;

/**
 * @ClassName: SendFactory
 * @Description: 普通工厂模式，就是建立一个工厂类，对实现了同一接口的一些类进行实例的创建。
 * @author qinf QQ:908247035
 * @date 2017年2月15日
 * @version V1.0
 */
public class SendFactory {

	public static Sender produce(String type){
		
		System.out.println("---------->type="+type);
		
		if("mail".equals(type)){
			return new MailSender();
		}else if("sms".equals(type)){
			return new SmsSender();
		}else{
			System.out.println("---------->没有匹配到type");
			return null;
		}
	}
}
