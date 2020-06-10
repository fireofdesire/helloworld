package com.pojo;

public class Dog 
{
    private String name;
    private String race;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRace() {
		return race;
	}
	public void setRace(String race) {
		this.race = race;
	}
	@Override
	public String toString() {
		return "Dog [name=" + name + ", race=" + race + "]";
	}
	public Dog() {
		super();
		System.out.println("Dog创建了");
	}
	public Dog(String name, String race) {
		super();
		this.name = name;
		this.race = race;
	}    
}
