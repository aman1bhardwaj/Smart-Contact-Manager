package com.scm.scm20.services.impl.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.scm.scm20.repositories.UserRepo;

@Service
public class SecurityCustomUserDetailsUserService implements UserDetailsService{

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        return userRepo.findByEmail(username)
        .orElseThrow(() -> (new UsernameNotFoundException("User not found with the mentioned user")));
        
    }

}
