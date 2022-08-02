package com.example.demo.security;

import com.example.demo.filter.TokenFilter;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.TokenService;
import com.example.demo.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final UserRepository repository;
    private final TokenService tokenService;


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.cors();
        http.csrf().disable();
        http.headers().frameOptions().disable();

        http.authorizeRequests()
            .antMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
            .antMatchers(HttpMethod.POST, "/auth").permitAll()
            .antMatchers(HttpMethod.POST, "/register").permitAll()
            .antMatchers(HttpMethod.POST, "/auth/forgot-password").permitAll()
            .antMatchers(HttpMethod.POST, "/auth/reset-password").permitAll()
            .antMatchers(HttpMethod.GET, "/auth/reset-password/validate").permitAll()
            .anyRequest().authenticated();

        http.addFilterBefore(new TokenFilter(tokenService, repository), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

}
