package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserPrincipalDetailsService userPrincipalDetailsService;

    public SecurityConfiguration(UserPrincipalDetailsService userPrincipalDetailsService) {
        this.userPrincipalDetailsService = userPrincipalDetailsService;
    }

    //eg 1. adding permissions manually
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser(passwordEncoder().encode("admin")).password("password").roles("ADMIN").authorities("ACCESS_TEST1", "ACCESS_TEST2","ROLE_ADMIN")
//                .and().withUser(passwordEncoder().encode("guest")).password("guest").roles("USER")
//                .and().withUser(passwordEncoder().encode("manager")).password("manager").roles("MANAGER").authorities("ACCESS_TEST1", "ROLE_MANAGER");
//    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    //This are the rules of what type of user have access to what pages !!!
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // eg 1
                //.anyRequest().authenticated()

                //.antMatchers("/index.html").permitAll()
                .antMatchers("/profile.html").authenticated()
                .antMatchers("/admin.html").hasRole("ADMIN")
                .antMatchers("/management.html").hasAnyRole("ADMIN","MANAGER")
                .antMatchers("/api/public/test1").hasAnyAuthority("ACCESS_TEST1")
                .antMatchers("/api/public/test2").hasAnyAuthority("ACCESS_TEST2")
                .and()
                //.httpBasic(); //this is a popup authentication and we replace it by login page

                //To make Spring Security use our login page.
                .formLogin().loginPage("/login").permitAll()
                .and()
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/login")
                // checkbox - remember account
                .and().rememberMe().tokenValiditySeconds(2592000).key("encoded!")
                ;
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.userPrincipalDetailsService);

        return daoAuthenticationProvider;
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
