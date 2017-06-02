package core.behavioral_patterns.strategy;

public class Multiply extends AbstractCalculator implements ICalculator {

	public int calculator(String exp) {

		int[] arrayInt = split(exp, "\\*");
		return arrayInt[0]*arrayInt[1];
	}

}
