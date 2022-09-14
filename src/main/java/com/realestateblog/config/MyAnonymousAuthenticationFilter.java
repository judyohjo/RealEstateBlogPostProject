package com.realestateblog.config;


/*import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

import com.realestateblog.model.CustomUserDetails;


public class MyAnonymousAuthenticationFilter extends AnonymousAuthenticationFilter {
    private static final String USER_SESSION_KEY = "user";
    private final String key;

    public MyAnonymousAuthenticationFilter(String key) {
        super(key);
        this.key = key;
    }

    @Override
    protected Authentication createAuthentication(HttpServletRequest req) {
        HttpSession httpSession = req.getSession();
        CustomUserDetails user = Optional.ofNullable((CustomUserDetails) httpSession.getAttribute(USER_SESSION_KEY))
                .orElseGet(() -> {
                	CustomUserDetails anon = new CustomUserDetails(null);
                    anon.setUsername("anonymousUser");
                    httpSession.setAttribute(USER_SESSION_KEY, anon);
                    return anon;
                });
        return new AnonymousAuthenticationToken(key, user, AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));
    }
}*/