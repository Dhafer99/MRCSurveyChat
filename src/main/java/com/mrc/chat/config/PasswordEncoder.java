package com.mrc.chat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class PasswordEncoder {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public String encode(String encodedPassword) {
        byte[] decodedBytes = Base64.getEncoder().encode(encodedPassword.getBytes());
        return new String(decodedBytes);
    }
}
