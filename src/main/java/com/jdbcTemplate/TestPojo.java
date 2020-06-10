package com.jdbcTemplate;

public class TestPojo 
{
    private String sex;
    private String userName;
	public String getSex() {
		return sex;
	}
	@Override
	public String toString() {
		return "TestPojo [sex=" + sex + ", userName=" + userName + "]";
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public TestPojo(String sex, String userName) {
		super();
		this.sex = sex;
		this.userName = userName;
	}
	public TestPojo() {
		super();
	}
    
}
