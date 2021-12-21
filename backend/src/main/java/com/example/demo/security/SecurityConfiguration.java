package com.example.demo.security;

import com.example.demo.filter.TokenFilter;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    @Autowired
    private AuthenticationServiceException authenticationService;

    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenService tokenService;

    //Configuration for authorization
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers(HttpMethod.POST, "/auth").permitAll()
            .anyRequest().authenticated()
            .and().csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().addFilterBefore(new TokenFilter(tokenService, repository), UsernamePasswordAuthenticationFilter.class);
    }

}
