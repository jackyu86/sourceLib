package core.behavioral_patterns.iterator;

public interface Iterator {

	/*前移*/
	public Object previous();
	
	/*后移*/
	public Object next();
	
	/*下一个值是否存在*/
	public boolean hasNext();
	
	/*取得第一个元素*/
	public Object first();
	
}
