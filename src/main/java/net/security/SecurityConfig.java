package net.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final AuthenticationSuccessHandler successhandler;

    @Autowired
    public SecurityConfig(@Qualifier("CustomUserDetailsService") UserDetailsService userDetailsService,
                          @Qualifier("CustomSuccesshandler") AuthenticationSuccessHandler successhandler) {
        this.userDetailsService = userDetailsService;
        this.successhandler = successhandler;

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().successHandler(successhandler)
                .and()
                .authorizeRequests()
                .antMatchers("/administrator/*").hasRole("ADMIN")
                .antMatchers("/", "/profile").authenticated();
    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

}
