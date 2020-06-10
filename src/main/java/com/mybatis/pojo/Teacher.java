package com.mybatis.pojo;

import java.util.List;

public class Teacher 
{
  private String tid;
  private String tname;
  private List<Student> students;
public Teacher(String tid, String tname, List<Student> students) {
	super();
	this.tid = tid;
	this.tname = tname;
	this.students = students;
}
public Teacher() {
	super();
}
@Override
public String toString() {
	return "Teacher [tid=" + tid + ", tname=" + tname + ", students=" + students + "]";
}
public String getTid() {
	return tid;
}
public void setTid(String tid) {
	this.tid = tid;
}
public String getTname() {
	return tname;
}
public void setTname(String tname) {
	this.tname = tname;
}
public List<Student> getStudents() {
	return students;
}
public void setStudents(List<Student> students) {
	this.students = students;
}
}
