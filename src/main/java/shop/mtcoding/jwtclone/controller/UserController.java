package shop.mtcoding.jwtclone.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.jwtclone.config.auth.JwtProvider;
import shop.mtcoding.jwtclone.config.auth.LoginUser;
import shop.mtcoding.jwtclone.model.User;
import shop.mtcoding.jwtclone.model.UserRepository;

// login usercheck
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserRepository userRepository;
    private final HttpSession session;

    // authorization check needed
    @GetMapping("/user")
    public ResponseEntity<?> user() {
        // assumption : if userid is 1, that person is permitted
        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");
        if (loginUser.getId() == 1) {
            return ResponseEntity.ok().body("success");
        } else {
            return new ResponseEntity<>("access denied", HttpStatus.FORBIDDEN); // 403
        }

    }

    @GetMapping("/") // no authorization needed
    public ResponseEntity<?> main() {
        return ResponseEntity.ok().body("access approved");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(User user) {
        Optional<User> userOP = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        // 1. Verified
        if (userOP.isPresent()) {
            String jwt = JwtProvider.create(userOP.get()); // with Prefix
            return ResponseEntity.ok().header(JwtProvider.HEADER, jwt).body("Successfully logged in"); // 200
        } else {
            return ResponseEntity.badRequest().build(); // non-exsist userinfo
        }
    }

}
