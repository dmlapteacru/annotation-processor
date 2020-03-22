package com.endava.tutorial.factory;

public class AnimalFactory {

	private AnimalFactory() {}

	public static com.endava.tutorial.factory.Animal create(String key) {
		switch(key) {
			case "BIRD": 
				return new com.endava.tutorial.factory.Bird();
			case "CAT": 
				return new com.endava.tutorial.factory.Cat();
			case "DOG": 
				return new com.endava.tutorial.factory.Dog();
			default: throw new UnsupportedOperationException();
		}
	}
}