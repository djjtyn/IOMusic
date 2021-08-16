package ioMusicPublic.authentication.instructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
public class InstructorSecurityConfig extends WebSecurityConfigurerAdapter{
	
	//Method to return a UserDetailsService consisting of the instructor/candidate details
	@Bean
	public UserDetailsService instructorDetailsService() {
		return new InstructorDetailsService();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//Instantiate a DaoAuthenticationProvider instance
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		//Instantiate a BCryptPasswordEncoder instance
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		//Set the DaoAuthentication Instances values
		authProvider.setUserDetailsService(instructorDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder);
		auth.authenticationProvider(authProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 http.authorizeRequests()
		 .and()
		 .formLogin()
		 	.loginPage("/instructor/login")
     		.loginProcessingUrl("/instructor/login")
     		.usernameParameter("email")
     		.defaultSuccessUrl("/")
     		.permitAll()
     	    .failureUrl("/instructor/loginFailure")
		 .and()
		 .logout()
     		.logoutUrl("/logout")
     		.logoutSuccessUrl("/")
     		.permitAll();        	
	}
}
