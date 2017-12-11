package core.structural_patterns.adapter;

/**
 * 
 * @ClassName: Wrapper
 * @Description: 适配器模式：
 * 对象适配器模式：
 * 适配器模式将某个类的接口转换成客户端期望的另一个接口表示，
 * 目的是消除由于接口不匹配所造成的类的兼容性问题。
 * 主要分为三类：类的适配器模式、对象的适配器模式、接口的适配器模式
 * @author qinf QQ:908247035
 * @date 2017年2月15日
 * @version V1.0
 */
public class Wrapper implements Targetable {

	private Source source = new Source();
	
	public void newMethod() {
		System.out.println("------------->Adapter.newMethod()");

	}

	public void method() {
		source.method();
	}

}
