package com.dekankilic.satisfying.service;

import com.dekankilic.satisfying.dto.AuthResponse;
import com.dekankilic.satisfying.dto.RegisterRequest;
import com.dekankilic.satisfying.exception.UserAlreadyExistsException;
import com.dekankilic.satisfying.exception.UserNotFoundException;
import com.dekankilic.satisfying.model.Cart;
import com.dekankilic.satisfying.model.User;
import com.dekankilic.satisfying.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final CartService cartService;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, CartService cartService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.cartService = cartService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElseThrow(() -> new UserNotFoundException("User not found with given username: " + username));
    }

    public Optional<User> getByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public AuthResponse createUser(RegisterRequest request){
        Optional<User> user = getByUsername(request.username());
        if(user.isPresent())
            throw new UserAlreadyExistsException("User already exists with the given username: " + request.username());

        User newUser = User.builder()
                .name(request.name())
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .authorities(request.authorities())
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .isEnabled(true)
                .accountNonLocked(true)
                .build();
        User savedUser = userRepository.save(newUser);
        Cart cart = Cart.builder()
                .customer(savedUser)
                .build();
        cartService.saveCart(cart);

        return AuthResponse.builder()
                .username(savedUser.getUsername())
                .message("Successfully registered")
                .build();
    }

    // This is newly added
    public User getUserFromJwt(String jwt){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }
}
