package org.hp.springsecurity.service;

import org.hp.springsecurity.model.LoginUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new LoginUser(username, "$2a$10$/jmysvcC7bV.Bohf2WO5S.M1NyWTq6uPplFXVbmTaBu/pibblkn2W", Arrays.asList("sys:user:list", "sys:role:list"));
    }
}
