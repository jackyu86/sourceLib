package core.behavioral_patterns.memento;

/**
 * 
 * @ClassName: Test
 * @Description: 备忘录模式：
 * Original类是原始类，里面有需要保存的属性value及创建一个备忘录类，
 * 用来保存value值。Memento类是备忘录类，Storage类是存储备忘录的类，
 * 持有Memento类的实例，该模式很好理解。
 * @author qinf QQ:908247035
 * @date 2017年2月16日
 * @version V1.0
 */
public class Test {

	public static void main(String[] args) {
		/*创建原始类*/
		Original original = new Original("egg");
		
		/*创建备忘录类*/
		Storage storage = new Storage(original.createMemento());
		
		
		//修改原始类状态
		System.out.println("------->"+original.getValue());
		original.setValue("niu");
		System.out.println("------->"+original.getValue());
		
		//还原原始类状态
		original.restMemento(storage.getMemento());
		System.out.println("-------->"+original.getValue());
	}
}
