package com.realestateblog;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.realestateblog.model.Account;
import com.realestateblog.repository.AccountRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)

public class AccountRepositoryTest {
	
	
    private TestEntityManager entityManager;
     
    @Autowired
    private AccountRepository repo;
    
    public void testCreateUser() {
        Account user = new Account();
        user.setEmail("abcd@gmail.com");
        user.setUsername("abcd");
        user.setPassword("abc1234");
        user.setName("오원재");
         
        Account savedUser = repo.save(user);
         
        Account existUser = entityManager.find(Account.class, savedUser.getId());
         
        assertThat(user.getEmail()).isEqualTo(existUser.getEmail());
         
    }
}
