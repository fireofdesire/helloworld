package com.mybatis.pojo;

public class Student 
{
    private String sid;
    private String sname;
    private String tno;
	public Student() {
		super();
	}
	public Student(String sid, String sname, String tno) {
		super();
		this.sid = sid;
		this.sname = sname;
		this.tno = tno;
	}
	@Override
	public String toString() {
		return "Student [sid=" + sid + ", sname=" + sname + ", tno=" + tno + "]";
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getTno() {
		return tno;
	}
	public void setTno(String tno) {
		this.tno = tno;
	}
}