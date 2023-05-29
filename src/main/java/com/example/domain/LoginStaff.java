package com.example.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class LoginStaff extends User{

	private static final long serialVersionUID = 1L;
	
	private final Staff staff;
	
	public LoginStaff(Staff staff, Collection<GrantedAuthority> authorityList) {
		super(staff.getMailAddress(), staff.getPassword(), authorityList);
		this.staff = staff;
	}
	
	public Staff getStaff() {
		return staff;
	}
	
}
