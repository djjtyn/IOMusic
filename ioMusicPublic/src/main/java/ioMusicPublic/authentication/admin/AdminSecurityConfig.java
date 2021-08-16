//This class deals with permissions required to view each web page
package ioMusicPublic.authentication.admin;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
@Order(2)
public class AdminSecurityConfig extends WebSecurityConfigurerAdapter{
	
	//Method to return a UserDetailsService consisting of the students details
	@Bean
	public UserDetailsService adminDetailsService() {
		return new AdminDetailsService();
	}
		
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//Instantiate a DaoAuthenticationProvider instance
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		//Instantiate a BCryptPasswordEncoder instance
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		//Set the DaoAuthentication Instances values
		authProvider.setUserDetailsService(adminDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder);
		auth.authenticationProvider(authProvider);
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 http
		 	.antMatcher("/admin/**")
		 	.authorizeRequests()
		 .and()
		 .formLogin()
		 	.loginPage("/admin/login")
  		.loginProcessingUrl("/admin/login")
  		.usernameParameter("email")
  		.defaultSuccessUrl("/")
  		.permitAll()
  	    .failureUrl("/admin/loginFailure")
		 .and()
		 .logout()
  		.logoutUrl("/logout")
  		.logoutSuccessUrl("/")
  		.permitAll();        	
	}
}
