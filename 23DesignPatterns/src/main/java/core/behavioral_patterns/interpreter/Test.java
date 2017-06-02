package core.behavioral_patterns.interpreter;

/**
 * 
 * @ClassName: Test
 * @Description: 解释器模式：
 * 一般主要应用在OOP开发中的编译器的开发中，所以适用面比较窄。
 * @author qinf QQ:908247035
 * @date 2017年2月16日
 * @version V1.0
 */
public class Test {

	 public static void main(String[] args) {  
		  
		 // 计算9+2-8的值  
	    int result = new Minus().interpret((new Context(new Plus()  
	            .interpret(new Context(9, 2)), 8)));  
	    System.out.println(result);  
	}  
}
