package core.structural_patterns.adapter;

import core.structural_patterns.decorator.Sourceable;

public class Source implements Sourceable{

	public void method(){
		System.out.println("------------>Source.method()");
	}
}
