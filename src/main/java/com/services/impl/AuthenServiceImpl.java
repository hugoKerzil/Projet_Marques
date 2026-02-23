package com.services.impl;

import com.dtos.AuthenDto;
import com.entities.Authen;
import com.mappers.AuthenMapper;
import com.repositories.AuthenRepository;
import com.services.AuthenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("authenService")
@Transactional
public class AuthenServiceImpl implements AuthenService{

    private final AuthenRepository authenRepository;
    private final AuthenMapper authenMapper;

    public AuthenServiceImpl(AuthenRepository authenRepository, AuthenMapper authenMapper) {
        this.authenRepository = authenRepository;
        this.authenMapper = authenMapper;
    }

    @Override
    public AuthenDto register(AuthenDto authenDto) {
        if (authenRepository.existsByPseudo(authenDto.getPseudo())) {
            throw new RuntimeException("Ce pseudo est déjà utilisé");
        }
        Authen authen = authenMapper.toEntity(authenDto);
        return authenMapper.toDto(authenRepository.save(authen));
    }

    @Override
    public AuthenDto login(String pseudo, String password) {
        Authen authen = authenRepository.findByPseudo(pseudo)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (!authen.getPassword().equals(password)) {
            throw new RuntimeException("Mot de passe incorrect");
        }

        return authenMapper.toDto(authen);
    }
}
