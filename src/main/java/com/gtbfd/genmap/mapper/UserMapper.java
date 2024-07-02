package com.gtbfd.genmap.mapper;

import com.gtbfd.genmap.domain.User;
import com.gtbfd.genmap.dto.UserDTO;
import com.gtbfd.genmap.vo.UserVO;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UserMapper {

    public static User toMap(UserDTO userDTO){
        return User.builder()
                .name(userDTO.name())
                .lastname(userDTO.lastname())
                .cpf(userDTO.cpf())
                .password(userDTO.password())
                .expiresIn(LocalDate.now().plusDays(30))
                .build();
    }

    public static UserDTO toDTO(User user){
        return new UserDTO(user.getName(), user.getLastname(), user.getCpf(), user.getPassword());
    }

    public static UserVO toVO(User user){
        return new UserVO(user.getId(), user.getName(), user.getLastname(), user.getExpiresIn());
    }
}
