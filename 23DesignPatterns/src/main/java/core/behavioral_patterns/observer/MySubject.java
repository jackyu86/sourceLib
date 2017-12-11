package core.behavioral_patterns.observer;

public class MySubject extends AbstractSubject{

	public void operation() {
		System.out.println("------------>MySubject.operation()");
		notifys();
	}


}
