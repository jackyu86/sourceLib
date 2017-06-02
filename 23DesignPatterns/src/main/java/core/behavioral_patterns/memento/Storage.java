package core.behavioral_patterns.memento;
/**
 * 
 * @ClassName: Storage
 * @Description: 存储备忘录的类
 * @author qinf QQ:908247035
 * @date 2017年2月16日
 * @version V1.0
 */
public class Storage {

	private Memento memento;

	public Storage(Memento memento) {
		super();
		this.memento = memento;
	}

	public Memento getMemento() {
		return memento;
	}

	public void setMemento(Memento memento) {
		this.memento = memento;
	}
	
}
