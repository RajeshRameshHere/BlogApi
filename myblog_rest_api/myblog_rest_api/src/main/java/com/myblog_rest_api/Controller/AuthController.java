package com.myblog_rest_api.Controller;

import com.myblog_rest_api.Entity.Role;
import com.myblog_rest_api.Entity.User;
import com.myblog_rest_api.Payload.JWTAuthResponse;
import com.myblog_rest_api.Payload.LoginDto;
import com.myblog_rest_api.Payload.SignupDto;
import com.myblog_rest_api.Repositories.RoleRepository;
import com.myblog_rest_api.Repositories.UserRepository;
import com.myblog_rest_api.Security.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private AuthenticationManager authenticationManager;
    private UserRepository userRepo;
    private RoleRepository roleRepo;
    private JwtTokenProvider jwtTokenProvider;


    public AuthController(AuthenticationManager authenticationManager,/**/JwtTokenProvider jwtTokenProvider, RoleRepository roleRepo, UserRepository userRepo) {
        this.authenticationManager = authenticationManager;
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
        this.jwtTokenProvider=jwtTokenProvider;
    }

    @PostMapping("/signin")
//    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto) {

    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto) {
        System.out.println("Received login request for username/email: " + loginDto.getUsernameOrEmail());
        System.out.println("Password: " + loginDto.getPassword());
//        try {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            loginDto.getUsernameOrEmail(), loginDto.getPassword()));
//
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//
//            //handeling exception
//            return new ResponseEntity<>("User signed in successfully", HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
//        }
////
//        After adding JWT tocken to the app

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // get token form tokenProvider
        String token = jwtTokenProvider.generateToken(authentication);
        return new ResponseEntity<>(new JWTAuthResponse(token), HttpStatus.OK);

    }

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody SignupDto signupDto) {
        if (userRepo.existsByEmail(signupDto.getEmail())) {
            return new ResponseEntity<>("email alreay exists", HttpStatus.BAD_REQUEST);
        }
        if (userRepo.existsByUsername(signupDto.getUsername())) {

            return new ResponseEntity<>("username already exists", HttpStatus.BAD_REQUEST);

        }

        User user = new User();
        user.setEmail(signupDto.getEmail());
        user.setName(signupDto.getName());
        user.setUsername(signupDto.getUsername());
        user.setPassword(signupDto.getPassword());

        Role role = roleRepo.findByName("ROLE_USER").get();
        user.setRoles(Collections.singleton(role));
        userRepo.save(user);

        return new ResponseEntity<>("user registered sucessfully", HttpStatus.CREATED);

    }
}

//}}


