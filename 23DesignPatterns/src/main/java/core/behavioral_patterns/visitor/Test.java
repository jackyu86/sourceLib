package core.behavioral_patterns.visitor;

/**
 * 
 * @ClassName: Test
 * @Description: 访问者模式：
 * 访问者模式就是一种分离对象数据结构与行为的方法，
 * 通过这种分离，可达到为一个被访问者动态添加新的操作而无需做其它的修改的效果。
 * @author qinf QQ:908247035
 * @date 2017年2月16日
 * @version V1.0
 */
public class Test {

	public static void main(String[] args) {
		Visitor visitor = new MyVisitor();  
        Subject sub = new MySubject();  
        sub.accept(visitor);
	}
}
