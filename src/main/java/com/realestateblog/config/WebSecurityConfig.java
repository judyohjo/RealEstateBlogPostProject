package com.realestateblog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.realestateblog.service.CustomUserDetailsService;
 

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig  {
	
	@Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
         
        return authProvider;
    }
	
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());

    }
    
    /*
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        	.antMatchers("about_us", "/login", "/register").permitAll()  
        	.antMatchers("/accounts", "/add_new_post", "/all_news_post", "/all_board_post").authenticated()          
            .and()
            .formLogin().loginPage("/login")
                .usernameParameter("username")
                .defaultSuccessUrl("/accounts")
                .permitAll()
            .and()
            .logout().logoutSuccessUrl("/").permitAll()
 			.and().csrf().disable();
        //http.anonymous().authenticationFilter(new MyAnonymousAuthenticationFilter(UUID.randomUUID().toString()));
    }*/
    
    /*
    public void configure(WebSecurity web) {
        web.ignoring()
            .antMatchers("/", "/homepage");
    }
	
*/
    
    
    /*@Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/", "/homepage", "/login");
       
    }*/
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests()
    	.antMatchers("/", "/homepage", "/about_us", "/login", "/register").permitAll()  
    	.antMatchers("/accounts", "/add_new_post", "/all_news_post", "/all_board_post").authenticated()          
        .and()
        .formLogin(form -> form
    			.loginPage("/login")
    			.defaultSuccessUrl("/homepage")
    			.permitAll()).authorizeRequests()
        
        .and()
        .logout().logoutSuccessUrl("/").permitAll()
			.and().csrf().disable();
		return http.build();
	}

	
	@Bean
	public UserDetailsService users() {
    UserDetails user = User.builder()
            .username("user")
            .password("password")
            .roles("USER")
            .build();
    
    return new InMemoryUserDetailsManager(user);
	}



}

/*@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    private DataSource dataSource;
     
    @Bean
    public CustomUserDetailsService customAccountService() {
        return new CustomUserDetailsService();
    }

    
    
    @Bean
    //public BCryptPasswordEncoder passwordEncoder() {
        //return new BCryptPasswordEncoder();
    public PasswordEncoder passwordEncoder() {
    	  //return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    		return new BCryptPasswordEncoder();
    }
     
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customAccountService());
        authProvider.setPasswordEncoder(passwordEncoder());
         
        return authProvider;
    }
 
    
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
        auth.inMemoryAuthentication()
        .withUser("user").password("{noop}password").roles("USER");
    }
    
 
    
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/account").authenticated()
            .anyRequest().permitAll()
            .and()
            .formLogin()
                .usernameParameter("email")
                .defaultSuccessUrl("/account")
                .permitAll()
            .and()
            .logout().logoutSuccessUrl("/").permitAll();
    }
     
     
}*/