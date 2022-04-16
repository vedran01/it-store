package com.itstore.configuration;

import com.itstore.security.SecurityService;
import com.itstore.security.filter.AuthenticationFilter;
import com.itstore.security.filter.JWTHeaderFilter;
import com.itstore.security.identity.IdentityDetailsService;
import com.itstore.security.token.JWTTokenService;
import com.itstore.security.token.TokenAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JWTTokenService tokenService;

    private final SecurityService securityService;

    private final IdentityDetailsService detailsService;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(detailsService);
        auth.authenticationProvider(new TokenAuthenticationProvider(securityService));

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors()
                .disable()
                .csrf()
                .disable();


        http.addFilter(new AuthenticationFilter(authenticationManager(), tokenService));
        http.addFilterAfter(new JWTHeaderFilter(authenticationManager(), tokenService), AuthenticationFilter.class);

        http.authorizeHttpRequests().anyRequest().authenticated();
    }
}
