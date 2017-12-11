package core.behavioral_patterns.iterator;

public class MyCollection implements Collection {

	private String s[] ={"A","B","C","D","E","F"};
	
	public Iterator iterator() {
		
		return new MyIterator(this);
	}

	public Object get(int i) {
		return s[i];
	}

	public int size() {
		return s.length;
	}

}
