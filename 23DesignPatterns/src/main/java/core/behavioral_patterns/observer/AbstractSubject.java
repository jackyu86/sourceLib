package core.behavioral_patterns.observer;

import java.util.Enumeration;
import java.util.Vector;

public abstract class AbstractSubject implements Subject{

	private Vector<Observer> vector = new Vector<Observer>();
	
	public void add(Observer observer) {
		vector.addElement(observer);
		
	}

	public void remove(Observer observer) {
		vector.remove(observer);
		
	}

	public void notifys() {
		Enumeration<Observer> enumeration = vector.elements();
		while(enumeration.hasMoreElements()){
			enumeration.nextElement().update();
		}
	}

}
