package com.tf.restocompras.config.jwt;


import com.tf.restocompras.error.UserNotFoundException;
import com.tf.restocompras.model.user.User;
import com.tf.restocompras.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationManager implements AuthenticationManager {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public JwtAuthenticationManager(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(String.format("Username %s not Found", email)));

        if (encoder.matches(password, user.getPassword())) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    user.getId(),
                    user.getPassword(),
                    user.getRole().getGrantedAuthorities());
            return usernamePasswordAuthenticationToken;
        } else {
            throw new UserNotFoundException("Invalid password");
        }
    }

}