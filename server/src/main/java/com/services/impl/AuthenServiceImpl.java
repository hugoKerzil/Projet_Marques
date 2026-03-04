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

    /**
     * Builder with dependency injection
     * Injection by constructor is preferred over @Autowired because:
     * - It makes dependencies mandatory
     * - It facilitates unit testing
     * - It enables immutability
     */
    public AuthenServiceImpl(AuthenRepository authenRepository, AuthenMapper authenMapper) {
        this.authenRepository = authenRepository;
        this.authenMapper = authenMapper;
    }

    /**
     * {@inheritDoc}
     * This method verifies whether a user can register and returns the register information if so.
     */
    @Override
    public AuthenDto register(AuthenDto authenDto) {
        if (authenRepository.existsByPseudo(authenDto.getPseudo())) {
            throw new RuntimeException("This username is already in use.");
        }
        Authen authen = authenMapper.toEntity(authenDto);
        return authenMapper.toDto(authenRepository.save(authen));
    }

    /**
     * {@inheritDoc}
     * This method verifies whether a user can login and returns the login information if so.
     */
    @Override
    public AuthenDto login(String pseudo, String password) {
        Authen authen = authenRepository.findByPseudo(pseudo)
                .orElseThrow(() -> new RuntimeException("User not found."));

        if (!authen.getPassword().equals(password)) {
            throw new RuntimeException("Incorrect password.");
        }

        return authenMapper.toDto(authen);
    }
}
