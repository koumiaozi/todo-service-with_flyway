package com.thoughtworks.training.kmj.todoservice.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UnauthorizedEntryPoint unauthorizedEntryPoint;

    @Autowired
    private TodoAuthFilter todoAuthFilter;


//    @Autowired
//    private JWTAuthenticationFilter jwtAuthenticationFilter;



    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
        System.out.println("-----dddd------------");
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                .antMatchers(HttpMethod.POST,"/users","/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(todoAuthFilter,
                        UsernamePasswordAuthenticationFilter.class)
//                    .addFilterBefore(new JWTLoginFilter("/login", authenticationManager()),
//                        UsernamePasswordAuthenticationFilter.class)
//                    .addFilterBefore(jwtAuthenticationFilter,
////                        UsernamePasswordAuthenticationFilter.class)
                    .exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint);
    }


}