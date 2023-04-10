package com.warders.api.common.config;

import com.warders.api.common.component.jwt.JwtTokenProvider;
import com.warders.api.common.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    private static final String[] WHITELIST = {
        "/swagger-ui/**", "/v3/api-docs/**"
    };
    private static final String PROFILE_PATH = "/v1/profiles/**";
    private static final String ADMIN_PROFILE_PATH = "/v1/admin/profiles/**";
    private static final String[] AUTH_PATH = {
        "/v1/users/**",
        "/v1/admin/**"
    };

    @Bean
    protected SecurityFilterChain config(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .httpBasic().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(WHITELIST).permitAll()

                // Auth
                .requestMatchers(HttpMethod.POST, AUTH_PATH).permitAll()
                .requestMatchers(HttpMethod.DELETE, AUTH_PATH).authenticated()

                // Profile
                .requestMatchers(HttpMethod.GET, PROFILE_PATH).permitAll()
                .requestMatchers(HttpMethod.POST, PROFILE_PATH).hasRole("USER")
                .requestMatchers(HttpMethod.DELETE, PROFILE_PATH).hasRole("USER")
                .requestMatchers(HttpMethod.DELETE, ADMIN_PROFILE_PATH).hasRole("ADMIN")

                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
            );

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
        throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
