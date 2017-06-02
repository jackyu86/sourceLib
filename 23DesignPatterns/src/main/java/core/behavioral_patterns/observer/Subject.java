package core.behavioral_patterns.observer;

public interface Subject {

	/*增加观察者*/
	public void add(Observer observer);
	
	/*删除观察者*/
	public void remove(Observer observer);
	
	/*通知所有观察者*/
	public void notifys();
	
	/*自身的操作*/
	public void operation();
}
