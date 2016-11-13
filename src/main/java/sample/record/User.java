package sample.record;

import java.util.Date;

public class User {
	private int id;
	private String name;
	private String encodedPassword;
	private Date createdOn;
	private Date updatedOn;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEncodedPassword() {
		return encodedPassword;
	}
	public void setEncodedPassword(String encodedPassword) {
		this.encodedPassword = encodedPassword;
	}
	public Date getCreateDate() {
		return createdOn;
	}
	public void setCreateDate(Date createDate) {
		this.createdOn = createDate;
	}
	public Date getUpdateDate() {
		return updatedOn;
	}
	public void setUpdateDate(Date updateDate) {
		this.updatedOn = updateDate;
	}
}
