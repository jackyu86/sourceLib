package core.behavioral_patterns.memento;

/**
 * 
 * @ClassName: Original
 * @Description: 原始类
 * @author qinf QQ:908247035
 * @date 2017年2月16日
 * @version V1.0
 */
public class Original {

	private String value;

	public Original(String value) {
		super();
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public Memento createMemento(){
		return new Memento(value);
	}
	
	public void restMemento(Memento memento){
		this.value = memento.getValue();
	}
}
