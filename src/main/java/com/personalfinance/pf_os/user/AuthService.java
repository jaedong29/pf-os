package com.personalfinance.pf_os.user;

import com.personalfinance.pf_os.security.JwtProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;

    }

    public User signup(String email, String rawPassword) {
        User user = new User();
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(rawPassword));
        User savedUser = this.userRepository.save(user);

        return savedUser;
    }

    public String login(String email, String rawPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("이메일 또는 비밀번호가 올바르지 않습니다"));
        String hasedPwd = user.getPasswordHash();
        if (!passwordEncoder.matches(rawPassword,hasedPwd)){
            throw new RuntimeException("이메일 또는 비밀번호가 올바르지 않습니다");
        }
        return jwtProvider.generateToken(email);
    }
}
