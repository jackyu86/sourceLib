package core.structural_patterns.adapter;

public class Test {

	public static void main(String[] args) {
		//类适配器测试
		Targetable targetable = new Adapter(); 
		
		targetable.method();
		targetable.newMethod();
		
		//对象适配器测试
		targetable = new Wrapper();
		
		targetable.method();
		targetable.newMethod();
		
		//接口适配器测试
		Sourceable sourceable = new SouceSub1();
		
		sourceable.method();
		
		sourceable = new SouceSub2();
		
		sourceable.newMethod();
	}
}
