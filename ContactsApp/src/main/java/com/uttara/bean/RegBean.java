package com.uttara.bean;

import java.io.Serializable;

public class RegBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String uname;
	private String email;
	private String pass;
	private String rpass;
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getRpass() {
		return rpass;
	}
	public void setRpass(String rpass) {
		this.rpass = rpass;
	}
	public RegBean() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean equals(Object obj) {
		RegBean r=(RegBean) obj;
		if(this.uname.equals(r.uname)&& email.equals(r.email)&& pass.equals(r.pass) && rpass.equals(r.rpass))
		{
			return true;
		}
		else
			return false;
	}
	@Override
	public int hashCode() {
		return (uname+email+pass+rpass).hashCode();
	}
	@Override
	public String toString() {
		return "RegBean : "+uname+":"+email+":"+pass+":"+rpass;
	}
}
