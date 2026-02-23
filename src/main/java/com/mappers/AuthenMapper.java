package com.mappers;

import com.dtos.AuthenDto;
import com.entities.Authen;
import org.springframework.stereotype.Component;

@Component
public class AuthenMapper {

    public AuthenDto toDto(Authen entity) {
        if (entity == null) return null;
        AuthenDto dto = new AuthenDto();
        dto.setId(entity.getId());
        dto.setPseudo(entity.getPseudo());
        dto.setPassword(entity.getPassword());
        dto.setRole(entity.getRole());

        return dto;
    }

    public Authen toEntity(AuthenDto dto) {
        if (dto == null) return null;
        Authen entity = new Authen();
        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        entity.setPseudo(dto.getPseudo());
        entity.setPassword(dto.getPassword());
        entity.setRole(dto.getRole() != null ? dto.getRole() : "USER");
        return entity;
    }
}
