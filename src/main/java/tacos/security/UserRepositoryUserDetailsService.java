package tacos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tacos.User;
import tacos.data.UserRepository;

@Service
public class UserRepositoryUserDetailsService implements UserDetailsService {
   private UserRepository userRepository;

   @Autowired
   public UserRepositoryUserDetailsService(final UserRepository userRepository) {
      this.userRepository = userRepository;
   }

   @Override
   public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
      final User user = userRepository.findByUsername(username);
      if(user != null){
         return user;
      }
      throw new UsernameNotFoundException("User '" + username + "' was not found");
   }
}
