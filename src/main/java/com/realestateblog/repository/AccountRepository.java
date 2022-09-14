package com.realestateblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.realestateblog.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{
	@Query("SELECT u FROM Account u WHERE u.username = ?1")
    public Account findByUsername(String email);

}
