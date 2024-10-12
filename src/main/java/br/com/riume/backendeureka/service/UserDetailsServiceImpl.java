package br.com.riume.backendeureka.service;

import br.com.riume.backendeureka.model.User;
import br.com.riume.backendeureka.repository.UserRepository;
import br.com.riume.backendeureka.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = repository.findByEmail(email);
        if (user.isPresent()) {
            User foundUser = user.get();
            return new UserSS(
                    foundUser.getUserId(),
                    foundUser.getEmail(),
                    foundUser.getPassword(),
                    foundUser.getActive(),
                    foundUser.getDeleted(),
                    foundUser.getRoles()
            );
        }
        throw new UsernameNotFoundException("User not found with email: " + email);
    }
}
