package com.revature.model;

import java.util.Objects;

public class fundUser {

	private int fundUser_id;
	private String fundUser_name;
	private String fundUser_password;
	private String fundUser_role;
	public fundUser(int fundUser_id, String fundUser_name, String fundUser_password, String fundUser_role) {
		super();
		this.fundUser_id = fundUser_id;
		this.fundUser_name = fundUser_name;
		this.fundUser_password = fundUser_password;
		this.fundUser_role = fundUser_role;
	}
	public fundUser() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getFundUser_id() {
		return fundUser_id;
	}
	public void setFundUser_id(int fundUser_id) {
		this.fundUser_id = fundUser_id;
	}
	public String getFundUser_name() {
		return fundUser_name;
	}
	public void setFundUser_name(String fundUser_name) {
		this.fundUser_name = fundUser_name;
	}
	public String getFundUser_password() {
		return fundUser_password;
	}
	public void setFundUser_password(String fundUser_password) {
		this.fundUser_password = fundUser_password;
	}
	public String getFundUser_role() {
		return fundUser_role;
	}
	public void setFundUser_role(String fundUser_role) {
		this.fundUser_role = fundUser_role;
	}
	@Override
	public int hashCode() {
		return Objects.hash(fundUser_id, fundUser_name, fundUser_password, fundUser_role);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		fundUser other = (fundUser) obj;
		return fundUser_id == other.fundUser_id && Objects.equals(fundUser_name, other.fundUser_name)
				&& Objects.equals(fundUser_password, other.fundUser_password)
				&& Objects.equals(fundUser_role, other.fundUser_role);
	}
	@Override
	public String toString() {
		return "fundUser [fundUser_id=" + fundUser_id + ", fundUser_name=" + fundUser_name + ", fundUser_password="
				+ fundUser_password + ", fundUser_role=" + fundUser_role + "]";
	}
}	
	