package com.gl.prateek.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.gl.prateek.serviceImpl.UserDetailsServiceImpl;


@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	 @Bean
	    public UserDetailsService userDetailsService() {
	        return new UserDetailsServiceImpl();
	    }
	     
	    @Bean
	    public BCryptPasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	     
	    @Bean
	    public AuthenticationManager authenticationManagerBean() throws Exception{
	    	return super.authenticationManagerBean();
	    }
	    @Bean
	    public DaoAuthenticationProvider authenticationProvider() {
	        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	        authProvider.setUserDetailsService(userDetailsService());
	        authProvider.setPasswordEncoder(passwordEncoder());
	         
	        return authProvider;
	    }
	 
	    @Override
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	       // auth.authenticationProvider(authenticationProvider());
	    	auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
	    }
	 
	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http.authorizeRequests()
	        	.antMatchers("/h2-console/").permitAll()
	        	.antMatchers("/api/user","/api/role").hasAuthority("ADMIN")
	        	.antMatchers(HttpMethod.POST,"api/employees/*").hasAuthority("ADMIN")
	        	.antMatchers(HttpMethod.PUT,"api/employees/*").hasAuthority("ADMIN")
	        	.antMatchers(HttpMethod.DELETE,"api/employees/*").hasAuthority("ADMIN")
	        	.anyRequest().authenticated()
	        	.and().httpBasic()
	        	.and().cors().and().csrf().disable();
	    }

		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/h2-console/**");
		}

}
