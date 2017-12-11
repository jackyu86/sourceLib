package core.behavioral_patterns.templateMethod;

/**
 * 
 * @ClassName: AbstractCalculator
 * @Description: 模板方法模式：
 * 一个抽象类中，有一个主方法，再定义1...n个方法，
 * 可以是抽象的，也可以是实际的方法，定义一个类，
 * 继承该抽象类，重写抽象方法，通过调用抽象类，
 * 实现对子类的调用，
 * @author qinf QQ:908247035
 * @date 2017年2月16日
 * @version V1.0
 */
public abstract class AbstractCalculator {

	/*主方法，实现对本类的其他方法调用*/
	public final int calculator(String exp, String opt){
		int array[] = split(exp, opt);
		
		return calculator(array[0], array[1]);
	}
	
	/*抽象方法，让子类去实现*/
	public abstract  int calculator(int num1, int num2);
	
	/*辅助方法*/
	public int[] split(String exp, String opt){
		String array[] = exp.split(opt);
		int arrayInt[] = new int[2];
		
		arrayInt[0] = Integer.parseInt(array[0]);
		arrayInt[1] = Integer.parseInt(array[1]);
		
		return arrayInt;
	}
}
