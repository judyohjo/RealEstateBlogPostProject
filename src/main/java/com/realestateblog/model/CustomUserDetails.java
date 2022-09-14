package com.realestateblog.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
 
public class CustomUserDetails implements UserDetails {
 
    private Account account;
     
    public CustomUserDetails(Account account) {
        this.account = account;
    }
 
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
 
    @Override
    public String getPassword() {
        return account.getPassword();
    }
    
    
    public String getConfirmPassword() {
        return account.getConfirmPassword();
    }
    
    
    public void setUsername(String username) {
    	account.setUsername(username);
    }
 
    @Override
    public String getUsername() {
        return account.getUsername();
    }
    
    public String getEmail() {
    	return account.getEmail();
    }
    
    public String getContact() {
    	return account.getContact();
    }
    
 
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
 
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
 
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
 
    @Override
    public boolean isEnabled() {
        return true;
    }
     
    public String getFullName() {
        return account.getName();
    }
 
}
