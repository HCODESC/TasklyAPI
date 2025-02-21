package com.hcodes.Taskly.user;

import com.hcodes.Taskly.dtos.AuthResponseDTO;
import com.hcodes.Taskly.dtos.LoginDto;
import com.hcodes.Taskly.dtos.RegisterDto;
import com.hcodes.Taskly.security.JWTGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JWTGenerator jwtGenerator;

    public AuthController(UserRepository userRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto user) {
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(user.getUsername());
            userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
            userEntity.setEmail(user.getEmail());
            userEntity.setFirstName(user.getFirstName());
            userEntity.setLastName(user.getLastName());
            userRepository.save(userEntity);

            return new ResponseEntity<>("User register success", HttpStatus.OK);
    }
}
