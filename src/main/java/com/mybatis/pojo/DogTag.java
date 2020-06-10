package com.mybatis.pojo;

public class DogTag 
{
  private String id;
  private String location;
public DogTag() {
	super();
}
public DogTag(String id, String location) {
	super();
	this.id = id;
	this.location = location;
}
@Override
public String toString() {
	return "DogTag [id=" + id + ", location=" + location + "]";
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getLocation() {
	return location;
}
public void setLocation(String location) {
	this.location = location;
}
}
