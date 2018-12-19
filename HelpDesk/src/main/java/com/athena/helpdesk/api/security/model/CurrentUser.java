package com.athena.helpdesk.api.security.model;

import com.athena.helpdesk.api.entity.User;

public class CurrentUser {

	private String token;
	private User usrer;
	
	public CurrentUser(String token, User usrer) {
		super();
		this.token = token;
		this.usrer = usrer;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public User getUsrer() {
		return usrer;
	}
	
	public void setUsrer(User usrer) {
		this.usrer = usrer;
	}

	
}
