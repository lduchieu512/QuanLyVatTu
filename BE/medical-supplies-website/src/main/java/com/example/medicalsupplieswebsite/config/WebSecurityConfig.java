package com.example.medicalsupplieswebsite.config;

import com.example.medicalsupplieswebsite.security.jwt.JwtEntryPoint;
import com.example.medicalsupplieswebsite.security.jwt.JwtFilter;
import com.example.medicalsupplieswebsite.security.userprinciple.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private JwtFilter jwtFilter;
    @Autowired
    private JwtEntryPoint jwtEntryPoint;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/api/v1/public/login",
                        "/api/v1/home/**",
                        "/api/v1/category/**",
                        "/api/v1/product/detail/**",
                        "/api/v1/customer-type/**",
                        "/api/v1/payment/**",
                        "/api/v1/cart/**")
                .permitAll()
                .antMatchers("/api/v1/employee/**").hasAnyRole("SALE", "ACCOUNTANT", "ADMIN")
                .antMatchers("/api/v1/customer/**").hasAnyRole("USER", "ADMIN", "ACCOUNTANT")
                .antMatchers("/api/v1/account/**").hasAnyRole("ADMIN", "USER", "SALE", "ACCOUNTANT")
                .antMatchers("api/v1/admin/**", "/api/v1/product/**", "/api/v1/productInfo/**").hasRole("ADMIN")
                .antMatchers("/api/v1/supply/**").hasRole("ADMIN")
                .antMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v1/shipment/shipment/**").hasAnyRole("SALE", "ACCOUNTANT", "ADMIN")
                .antMatchers("/api/v1/shipment/return/**").hasAnyRole("ACCOUNTANT", "ADMIN")
                .antMatchers("/api/v1/receipt").hasAnyRole("ACCOUNTANT", "ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .cors()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
