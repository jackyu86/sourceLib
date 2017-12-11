package core.behavioral_patterns.mediator;

public abstract class User {

	private Mediator mediator;  
	
	
	public User(Mediator mediator) {
		super();
		this.mediator = mediator;
	}

	public abstract void work();

	public Mediator getMediator() {
		return mediator;
	}  
	
}
