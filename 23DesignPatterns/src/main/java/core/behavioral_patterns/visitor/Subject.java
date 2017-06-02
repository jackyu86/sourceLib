package core.behavioral_patterns.visitor;

public interface Subject {

	public void accept(Visitor visitor);
	
	public String getSubject();
	
}
