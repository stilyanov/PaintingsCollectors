package com.paintingscollectors.service;

import com.paintingscollectors.config.UserSession;
import com.paintingscollectors.model.dto.UserLoginDTO;
import com.paintingscollectors.model.dto.UserRegisterDTO;
import com.paintingscollectors.model.entity.Painting;
import com.paintingscollectors.model.entity.User;
import com.paintingscollectors.repository.PaintingRepository;
import com.paintingscollectors.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserSession userSession;
    private final PaintingRepository paintingRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserSession userSession, PaintingRepository paintingRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userSession = userSession;
        this.paintingRepository = paintingRepository;
    }

    public boolean register(UserRegisterDTO registerDTO) {
        Optional<User> existingUser = this.userRepository.findByUsernameOrEmail(registerDTO.getUsername(), registerDTO.getEmail());

        if (existingUser.isPresent()) {
            return false;
        }

        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        this.userRepository.save(user);

        return true;
    }

    public boolean login(UserLoginDTO loginDTO) {
        Optional<User> optionalUser = this.userRepository.findByUsername(loginDTO.getUsername());

        if (optionalUser.isEmpty()) {
            return false;
        }

        boolean matches = passwordEncoder.matches(loginDTO.getPassword(), optionalUser.get().getPassword());

        if (!matches) {
            return false;
        }

        this.userSession.login(optionalUser.get().getId(), loginDTO.getUsername());

        return true;
    }

    public void logout() {
        this.userSession.logout();
    }

    public Set<Painting> findFavourites(Long id) {
        Optional<User> byId = this.userRepository.findById(id);

        if (byId.isEmpty()) {
            return new HashSet<>();
        }

        return byId.get().getFavouritePaintings();
    }

}
