package core.structural_patterns.bridge;

public class Test {

	public static void main(String[] args) {
		Bridge bridge = new MyBridge();
		Sourceable source = new SouceSub1();
		bridge.setSource(source);
		bridge.method();
		source = new SouceSub2();
		bridge.setSource(source);
		bridge.method();
		
	}
}
