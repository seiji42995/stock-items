package com.example.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.domain.LoginStaff;
import com.example.domain.Staff;
import com.example.repository.StaffRepository;

@Service
public class StaffDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private StaffRepository staffRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String mailAddress) throws UsernameNotFoundException {
		Staff staff = staffRepository.findByMailAddress(mailAddress);
		if(staff == null) {
			throw new UsernameNotFoundException("そのメールアドレスは登録されていません");
		}
		
		Collection<GrantedAuthority> authorityList = new ArrayList<>();
		authorityList.add(new SimpleGrantedAuthority("ROLE_USER"));
		return new LoginStaff(staff, authorityList);
	}

}
