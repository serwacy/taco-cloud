package tacos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

   @Override
   protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
      auth.ldapAuthentication()
              .userSearchBase("ou=people")
              .userSearchFilter("(uid={0})")
              .groupSearchBase("ou=groups")
              .groupSearchFilter("member={0}")
              .passwordCompare()
              .passwordEncoder(new BCryptPasswordEncoder())
              .passwordAttribute("passcode");
      //in book it is shown that ldapAuthentication() can be extended by the line below
      //yet it seems that contextSource() is exit method, and there can be no other methods after it
      //which is the same case with passwordCompare(), so we can't really use both
      //   .contextSource().root("dc=tacocloud,dc=com").ldif("classpath:users.ldif")
   }
}
