package com.springmvc;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Writer 
{
  
  private String Name;
  //加上@JsonIgnore注解后，ajax请求Json数据时，回传的数据忽略掉sex属性
  private String Sex;
@Override
public String toString() {
	return "Writer [Name=" + Name + ", Sex=" + Sex + "]";
}
public Writer() {
	super();
}
public Writer(String name, String sex) {
	super();
	Name = name;
	Sex = sex;
}
//@JsonProperty("Name")
public String getName() {
	return Name;
}
public void setName(String name) {
	Name = name;
}
//@JsonProperty("Sex")
public String getSex() {
	return Sex;
}
public void setSex(String sex) {
	Sex = sex;
}
}
