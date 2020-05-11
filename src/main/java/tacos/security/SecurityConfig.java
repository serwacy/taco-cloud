package tacos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
   @Autowired
   private UserDetailsService userDetailsService;

   @Override
   protected void configure(final HttpSecurity http) throws Exception {
      http.authorizeRequests()
              .antMatchers("/design", "/order").access("hasRole('ROLE_USER')")
              .antMatchers("/", "/**").access("permitAll")
              .and()
              .formLogin().loginPage("/login")
              .defaultSuccessUrl("/design")
              .and()
              .logout()
              .logoutSuccessUrl("/");
   }

   @Bean
   public PasswordEncoder encoder() {
      return new BCryptPasswordEncoder();
   }

   @Override
   protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(userDetailsService)
              .passwordEncoder(encoder());
   }
}
