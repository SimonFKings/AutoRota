package autorota.services;

import autorota.domain.UserInfo;
import autorota.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        UserInfo domainUser = userRepo.findByEmail(email);

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        if (domainUser.getRole().getRole().equals("ADMIN")) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else if (domainUser.getRole().getRole().equals("EMPLOYEE")) {
            authorities.add(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
        }


        return new User(
                domainUser.getEmail(),
                domainUser.getPassword(),
                true,
                true,
                true,
                true,
                authorities
        );
    }

}
