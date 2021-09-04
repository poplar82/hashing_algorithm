package com.topolski.hashing_algorithm.services;

import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderService implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        StringBuilder pass = new StringBuilder(charSequence.toString().repeat(50 / charSequence.length() + 1).substring(0, 50));
        int[] passArray = new int[50];
        for (int i = 0; i < passArray.length; i++) {
            passArray[i] = (int)pass.charAt(i) + passArray.length - i;
        }
        StringBuilder password = new StringBuilder();
        for (int i : passArray) {
            password.append(Character.toString(i));
        }
        return password.toString();
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return encode(charSequence).equals(s);
    }
}
