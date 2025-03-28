package com.basic.myspringboot.security.config;

import com.basic.myspringboot.security.service.UserInfoUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //InMemory Test 용 adduser 후엔 블럭 처리함~
    /*@Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
    
        //test를 위해 InMemoryUserDetailsManager 객체로 계정 생성
        UserDetails admin = User.withUsername("adminboot")
                .password(encoder.encode("pwd1"))
                .roles("ADMIN")
                .build();

        UserDetails user = User.withUsername("userboot")
                .password(encoder.encode("pwd2"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }*/

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserInfoUserDetailsService();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/api/users/welcome","/userinfos/new").permitAll()
                            .requestMatchers("/api/users/**").authenticated();
                })
                .formLogin(withDefaults())
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        
        //DaoAuthenticationProvider에게 UserDetailsService 와 PasswordEncoder를 설정
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
}
