package com.myblog_rest_api.config;

import com.myblog_rest_api.Security.CustomAuthenticationEntryPoint;
import com.myblog_rest_api.Security.CustomerUserDetailsService;
import com.myblog_rest_api.Security.JwtAuthenticationFilter;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.server.WebFilterChain;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private CustomerUserDetailsService customerUserDetailsService;
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;


    @Autowired
    public SecurityConfig(CustomerUserDetailsService customerUserDetailsService,
                          CustomAuthenticationEntryPoint customAuthenticationEntryPoint) {

        this.customerUserDetailsService = customerUserDetailsService;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
    }

    //1.userdetails


    public void userDetailsService(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(customerUserDetailsService).passwordEncoder(passwordEncoder());

//        UserDetails user_1 = User.withUsername("raju")
//                .password(enocder.encode("testing"))
//                .roles("ADMIN")
//                .build();
//
//        UserDetails user_2 = User.withUsername("ramesh")
//                .password(enocder.encode("raju"))
//                .roles("USER")
//                .build();
//
//
//        return new InMemoryUserDetailsManager(user_1,user_2);
    }

    //2.bcrypt the password for extra layer of protection

    //depricated
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return authenticationManagerBean();
//    }
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(customerUserDetailsService)
                .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //pass the passwordEncoder object(BcryptPasswordEncoder) to the userDetails to encrypt

    //3.SecurityFilterChain configuration and pass HttpSecurity as arrgs

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

//        http.csrf().disable()
//                .authorizeHttpRequests()
//                .requestMatchers(HttpMethod.GET,"/api/auth")
//                .permitAll()
//                .and()
//                .authorizeHttpRequests()
//                .requestMatchers(HttpMethod.POST,"/api/auth/**")
//                .permitAll()
//                .and()
//                .authorizeHttpRequests()
//                .requestMatchers("/api/posts/**")
//                .authenticated()
//                .and()
//                .httpBasic();
//
//
//        return http.build();

        http
                .csrf().disable()
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers(HttpMethod.GET, "/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
                        .requestMatchers("/api/posts/**").authenticated()
                        .anyRequest().authenticated()
                )
                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .and()
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
   

        return http.build();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

}





//
//http.csrf().disable().authorizeHttpRequests()
//                .requestMatchers("/")
//
//                .permitAll()
//                .and()
//                .authorizeRequests()
//                .requestMatchers("/")
//                .authenticated()
//                .and().formLogin()
//                .and().build();



/*

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder){
        //user 1
        UserDetails user= User.withUsername("raju")
                .password(encoder.encode("raju"))
                .roles("ADMIN")
                .build();
        //user 2
        UserDetails user1=User.withUsername("ramesh")
                .password(encoder.encode("raju"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user,user1);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return  http.csrf().disable().authorizeHttpRequests().requestMatchers("/api").permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers("/api/posts/**").authenticated()
                .and().httpBasic().and().build();
  }
    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }

 */