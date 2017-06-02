package core.creational_patterns.builder;

/**
 * @ClassName: Builder
 * @Description: 建造者模式：
 * 工厂类模式提供的是创建单个类的模式，
 * 而建造者模式则是将各种产品集中起来进行管理，
 * 用来创建复合对象，所谓复合对象就是指某个类具有不同的属性，
 * 其实建造者模式就是前面抽象工厂模式和最后的Test结合起来得到的。
 * @author qinf QQ:908247035
 * @date 2017年2月15日
 * @version V1.0
 */
public class Builder {

	public static void produceMailSender(){
		System.out.println("----------->Builder.produceMailSender()");
	}
	
	public static void produceSmsSender(){
		System.out.println("----------->Builder.produceSmsSender()");
	}
	
	public static void main(String[] args) {
		produceMailSender();
		produceSmsSender();
	}
}
