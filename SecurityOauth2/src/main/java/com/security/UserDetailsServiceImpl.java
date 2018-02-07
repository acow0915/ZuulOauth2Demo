package com.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

@Component
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	//aqnmuqplu1rhfcevg1o0q2ga77
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<UserDetails> userDetailsList = new ArrayList<>();
        userDetailsList.add(new User("tim","123qwe", Lists.newArrayList(new SimpleGrantedAuthority("READ"))));
        userDetailsList.add(new User("admin","123qwe", Lists.newArrayList(new SimpleGrantedAuthority("READ"), new SimpleGrantedAuthority("WRITE"))));
		
        return userDetailsList.stream().filter(u -> u.getUsername().equals(username))
        			   .findAny().get();
	}

}
