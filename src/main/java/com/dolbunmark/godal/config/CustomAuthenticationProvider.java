package com.dolbunmark.godal.config;

import com.dolbunmark.godal.domain.User;
import com.dolbunmark.godal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;

    @Autowired
    public CustomAuthenticationProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        User user = null;

        Optional<User> value = userRepository.findByLogin(authentication.getName());
        if (value.isPresent()) {
            user = value.get();
        }

        if (Objects.nonNull(user)
                && user.getPassword().equals(authentication.getCredentials().toString())) {
            return new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword());
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
