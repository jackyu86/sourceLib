package core.behavioral_patterns.state;
/**
 * 
 * @ClassName: Test
 * @Description: 状态模式：
 * 当对象的状态改变时，同时改变其行为。
 * 根据这个特性，状态模式在日常开发中用的挺多的，
 * 尤其是做网站的时候，我们有时希望根据对象的某一属性，
 * 区别开他们的一些功能，比如说简单的权限控制等。
 * @author qinf QQ:908247035
 * @date 2017年2月16日
 * @version V1.0
 */
public class Test {

	public static void main(String[] args) {
		State state = new State();
		Context context = new Context(state);
		
		state.setValue("state1");
		context.method();
		state.setValue("state2");
		context.method();
	}
}
