package shop.mtcoding.jwtclone.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.jwtclone.model.User;
import shop.mtcoding.jwtclone.model.UserRepository;

// login usercheck
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(User user) {
        userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());

    }

}
