package core.behavioral_patterns.visitor;

public class MyVisitor implements Visitor {

	public void visit(Subject sub) {
		System.out.println("------------>MyVisitor.visit()"+sub.getSubject());

	}

}
