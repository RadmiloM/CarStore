package com.cars.RentACar.configuration;

import com.cars.RentACar.user.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class GlobalConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        var daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .ignoringAntMatchers("/**")
                .and()
                .cors()
                .disable()
                .authorizeRequests()
                .antMatchers("/cars").permitAll()
                .antMatchers("/users/register").permitAll()
                .antMatchers("/users/login/{userId}").permitAll()
                .antMatchers("/users/{id}").permitAll()
                .antMatchers("/users").hasAuthority("ADMIN")
                .antMatchers("/admin/update/{id}").hasAuthority("ADMIN")
                .antMatchers("/cars").hasAuthority("ADMIN")
                .antMatchers("/cars/search").permitAll()
                .antMatchers(HttpMethod.GET,"/cars/{carId}").permitAll()
                .antMatchers(HttpMethod.PATCH,"/cars/{carId}").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/cars/{carId}").hasAuthority("ADMIN")
                .antMatchers("/cars/available").permitAll()
                .antMatchers("/cars/available/search").permitAll()
                .antMatchers("/cars/{carId}/calendar").permitAll()
                .antMatchers(HttpMethod.POST,"/contracts").permitAll()
                .antMatchers(HttpMethod.GET,"/contracts").hasAuthority("ADMIN")
                .antMatchers("/contracts/sample").permitAll()
                .antMatchers("/contracts/pending").hasAuthority("ADMIN")
                .antMatchers("/contracts/{contractId}/approval").hasAuthority("ADMIN")
                .antMatchers("/contracts/{userId}/history").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }


}
