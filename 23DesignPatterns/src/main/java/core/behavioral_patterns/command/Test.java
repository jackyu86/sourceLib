package core.behavioral_patterns.command;
/**
 * 
 * @ClassName: Test
 * @Description: 命令模式：
 * 命令模式的目的就是达到命令的发出者和执行者之间解耦，
 * 实现请求和执行分开，熟悉Struts的同学应该知道，
 * Struts其实就是一种将请求和呈现分离的技术，
 * 其中必然涉及命令模式的思想
 * @author qinf QQ:908247035
 * @date 2017年2月16日
 * @version V1.0
 */
public class Test {

	public static void main(String[] args) {
		
		Receiver receiver = new Receiver();
		
		Command command = new MyCommand(receiver );
		
		Invoker invoker = new Invoker(command );
		
		invoker.action();
	}
}
