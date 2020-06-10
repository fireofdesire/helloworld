package com.mybatis.pojo;

public class Dog 
{
  private String dogId;
  private String dogName;
  private DogTag dogTag;
public String getDogId() {
	return dogId;
}
public void setDogId(String dogId) {
	this.dogId = dogId;
}
public String getDogName() {
	return dogName;
}
public void setDogName(String dogName) {
	this.dogName = dogName;
}
public DogTag getDogTag() {
	return dogTag;
}
public void setDogTag(DogTag dogTag) {
	this.dogTag = dogTag;
}
@Override
public String toString() {
	return "Dog [dogId=" + dogId + ", dogName=" + dogName + ", dogTag=" + dogTag + "]";
}
public Dog(String dogId, String dogName, DogTag dogTag) {
	super();
	this.dogId = dogId;
	this.dogName = dogName;
	this.dogTag = dogTag;
}
public Dog() {
	super();
}

}
