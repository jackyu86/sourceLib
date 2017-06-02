package core.structural_patterns.decorator;

/**
 * 
 * @ClassName: Decorator
 * @Description: 装饰模式：
 * 顾名思义，装饰模式就是给一个对象增加一些新的功能，
 * 而且是动态的，要求装饰对象和被装饰对象实现同一个接口，
 * 装饰对象持有被装饰对象的实例，
 * @author qinf QQ:908247035
 * @date 2017年2月15日
 * @version V1.0
 */
public class Decorator implements Sourceable{

	private Sourceable source = new Source();
	
	public void method() {
		System.out.println(" do it before....");

		source.method();
		
		System.out.println(" do it after....");
	}

}
