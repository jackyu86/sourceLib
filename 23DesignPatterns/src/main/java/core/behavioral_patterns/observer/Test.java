package core.behavioral_patterns.observer;
/**
 * 
 * @ClassName: Test
 * @Description: 观察者模式：
 * MySubject类就是我们的主对象，
 * Observer1和Observer2是依赖于MySubject的对象，
 * 当 MySubject变化时，Observer1和Observer2必然变化。
 * AbstractSubject类中定义着需要监控的对象列表，
 * 可以对其进 行修改：增加或删除被监控对象，
 * 且当MySubject变化时，负责通知在列表内存在的对象。
 * @author qinf QQ:908247035
 * @date 2017年2月16日
 * @version V1.0
 */
public class Test {

	public static void main(String[] args) {
		AbstractSubject abstractSubject = new MySubject();
		Observer observer1 = new Observer1();
		Observer observer2 = new Observer2();
		
		abstractSubject.add(observer1);
		abstractSubject.add(observer2);
		abstractSubject.operation();
		
	}
}
