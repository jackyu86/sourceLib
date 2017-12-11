package core.behavioral_patterns.iterator;

public interface Collection {

	/*遍历集合*/
	public Iterator iterator();
	
	/*获取集合的一个元素*/
	public Object get(int i);
	
	/*获取集合大小*/
	public int size();
}
