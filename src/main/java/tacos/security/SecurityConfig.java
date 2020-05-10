package tacos.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

   @Override
   protected void configure(final HttpSecurity http) throws Exception {
      http.authorizeRequests().antMatchers("/info/**").hasAnyRole("ADMIN","ROLE_USER").
              and().formLogin();
   }

   @Override
   protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
      auth.inMemoryAuthentication()
              .withUser("buzz")
              .password("infinity")
              .authorities("ROLE_USER")
              .and()
              .withUser("woody")
              .password("bullseye")
              .authorities("ROLE_USER");
   }
}
