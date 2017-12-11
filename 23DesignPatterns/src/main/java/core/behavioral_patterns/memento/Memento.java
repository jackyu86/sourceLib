package core.behavioral_patterns.memento;

/**
 * 
 * @ClassName: Memento
 * @Description: 备忘录类
 * @author qinf QQ:908247035
 * @date 2017年2月16日
 * @version V1.0
 */
public class Memento {

	private String value;

	public Memento(String value) {
		super();
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
