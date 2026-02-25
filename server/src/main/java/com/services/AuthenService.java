package com.services;
import com.dtos.AuthenDto;

public interface AuthenService {
    AuthenDto register(AuthenDto authenDto);
    AuthenDto login(String pseudo, String motDePasse);
}