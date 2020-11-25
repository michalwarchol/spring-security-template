package com.example.springsecurity.security;

import com.example.springsecurity.auth.ApplicationUserSevice;
import com.example.springsecurity.jwt.JWTConfig;
import com.example.springsecurity.jwt.JwtTokenVerifier;
import com.example.springsecurity.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;

import static com.example.springsecurity.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserSevice applicationUserSevice;
    private final JWTConfig jwtConfig;
    private final SecretKey secretKey;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder,
                                     ApplicationUserSevice applicationUserService,
                                     JWTConfig jwtConfig,
                                     SecretKey secretKey) {
        this.passwordEncoder = passwordEncoder;
        this.applicationUserSevice = applicationUserService;
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig, secretKey))
                .addFilterAfter(new JwtTokenVerifier(secretKey, jwtConfig),JwtUsernameAndPasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                .antMatchers("/api/**").hasRole(STUDENT.name())
                .anyRequest()
                .authenticated();
                //.and()
                //.formLogin()
                //    .loginPage("/login")
                //    .permitAll()
                //    .defaultSuccessUrl("/courses", true)
                //    .passwordParameter("password")
                //    .usernameParameter("username")
                //.and()
                //.rememberMe()
                //    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
                //    .key("something secured")
                //    .rememberMeParameter("remember-me")
                //.and()
                //.logout()
                //    .logoutUrl("/logout")
                //    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                //    .clearAuthentication(true)
                //    .invalidateHttpSession(true)
                //    .deleteCookies("JSESSIONID", "remember-me")
                //    .logoutSuccessUrl("/login");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserSevice);
        return provider;
    }
}
