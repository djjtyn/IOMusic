package ioMusicPublic.authentication.student;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@EnableWebSecurity
@Configuration
@Order(1)
public class StudentSecurityConfig extends WebSecurityConfigurerAdapter{
	
	//Method to return a UserDetailsService consisting of the students details
	@Bean
	public UserDetailsService studentDetailsService() {
		return new StudentDetailsService();
	}
		
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//Instantiate a DaoAuthenticationProvider instance
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		//Instantiate a BCryptPasswordEncoder instance
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		//Set the DaoAuthentication Instances values
		authProvider.setUserDetailsService(studentDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder);
		auth.authenticationProvider(authProvider);
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 http
		 	.antMatcher("/student/**")
		 	.authorizeRequests()
		 .and()
		 .formLogin()
		 	.loginPage("/student/login")
     		.loginProcessingUrl("/student/login")
     		.usernameParameter("email")
     		.defaultSuccessUrl("/", true)
     		.failureUrl("/student/loginFailure")
     	.permitAll()   
		 .and()
		 .logout()
     		.logoutUrl("/logout")
     		.logoutSuccessUrl("/")
     		.permitAll();   
	}

	
	 @Override
	 public void configure(WebSecurity web) {
		 web.ignoring()
	     	.antMatchers("/resources/**", "/static/**", "/login**");
	 }
}
