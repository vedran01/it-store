package com.itstore.configuration;

import com.itstore.security.SecurityService;
import com.itstore.security.filter.AuthenticationFilter;
import com.itstore.security.filter.JWTHeaderFilter;
import com.itstore.security.identity.IdentityDetailsService;
import com.itstore.security.token.JWTTokenService;
import com.itstore.security.token.TokenAuthenticationProvider;
import com.itstore.security.twofactor.S2faAuthenticator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    private final JWTTokenService tokenService;

    private final SecurityService securityService;

    private final IdentityDetailsService detailsService;

    private final S2faAuthenticator authenticator;

    private final ApplicationEventPublisher eventPublisher;

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

        http.cors().configurationSource(corsConfigurationSource());
        http.csrf().disable();

        http.addFilter(new AuthenticationFilter(authenticationManager(), tokenService, authenticator, eventPublisher));
        http.addFilterAfter(new JWTHeaderFilter(authenticationManager(), tokenService), AuthenticationFilter.class);

        http.authorizeHttpRequests().anyRequest().authenticated();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOriginPattern("*");
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
