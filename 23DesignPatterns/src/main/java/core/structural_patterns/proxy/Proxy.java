package core.structural_patterns.proxy;

/**
 * 
 * @ClassName: Proxy
 * @Description: 代理模式：
 * 代理模式就是多一个代理类出来，替原对象进行一些操作
 * @author qinf QQ:908247035
 * @date 2017年2月15日
 * @version V1.0
 */
public class Proxy implements Sourceable{

	private Sourceable source = new Source();
	
	public void method() {
		before();
		
		source.method();
		
		after();
	}

	public static void before(){
		System.out.println("before() do it before....");
		
	}
	
	public static void after(){
		System.out.println("after() do it before....");
		
	}
}
