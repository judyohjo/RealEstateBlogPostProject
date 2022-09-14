package com.realestateblog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.realestateblog.model.Account;
import com.realestateblog.model.CustomUserDetails;
import com.realestateblog.repository.AccountRepository;

public class CustomUserDetailsService implements UserDetailsService {
    
    @Autowired
    private AccountRepository accountRepo;
     
    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepo.findByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUserDetails(account);
       
        
    }
 
}