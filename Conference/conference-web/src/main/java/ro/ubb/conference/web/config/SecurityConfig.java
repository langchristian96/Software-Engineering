package ro.ubb.conference.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import ro.ubb.conference.web.security.AuthFailure;
import ro.ubb.conference.web.security.AuthSuccess;
import ro.ubb.conference.web.security.EntryPointUnauthorizedHandler;
import ro.ubb.conference.web.security.UserDetailServiceImpl;

/**
 * Created by langchristian96 on 5/18/2017.
 */


@Configuration
@EnableWebSecurity
@ComponentScan("ro.ubb.conference.web.security")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthFailure authFailure;

    @Autowired
    private AuthSuccess authSuccess;

    @Autowired
    private EntryPointUnauthorizedHandler unauthorizedHandler;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    public void configAuthBuilder(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable().headers().frameOptions().disable().and()
//                .exceptionHandling()
//                .authenticationEntryPoint(unauthorizedHandler)
//                .and()
//                .authorizeRequests()
//                .antMatchers("/api/persons").anonymous()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginProcessingUrl("/api/login")
//                .usernameParameter("username")
//                .passwordParameter("password")
//                .successHandler(authSuccess)
//                .failureHandler(authFailure)
//                .permitAll()
//                .and()
//                .logout();


        http
                .csrf().disable()

                .authorizeRequests()

                .antMatchers("/login*").anonymous()
                .antMatchers("/api/persons*").anonymous()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .successHandler(authSuccess)
                .failureHandler(authFailure)
//                .failureUrl("/login.html?error=true")
                .and()
                .logout().logoutSuccessUrl("/login.html");
    }
}