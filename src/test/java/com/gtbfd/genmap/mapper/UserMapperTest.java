package com.gtbfd.genmap.mapper;

import com.gtbfd.genmap.domain.User;
import com.gtbfd.genmap.dto.UserDTO;
import com.gtbfd.genmap.vo.UserVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    public void init(){
        userMapper = new UserMapper();
    }

    @Test
    public void whenToMapThenReturnUser(){
        UserDTO userDTO = new UserDTO("Roberto", "A. Almeida", "526.928.510-05", "a9q8s6a1!#");

        User user = User.builder()
                .name(userDTO.name())
                .lastname(userDTO.lastname())
                .cpf(userDTO.cpf())
                .password(userDTO.password())
                .expiresIn(LocalDate.now().plusDays(30))
                .build();

        User userResponse = userMapper.toMap(userDTO);

        Assertions.assertEquals(user.getName(), userResponse.getName());
        Assertions.assertEquals(user.getLastname(), userResponse.getLastname());
        Assertions.assertEquals(user.getCpf(), userResponse.getCpf());
        Assertions.assertEquals(user.getPassword(), userResponse.getPassword());
        Assertions.assertEquals(user.getExpiresIn(), userResponse.getExpiresIn());

    }

    @Test
    public void whenToDTOThenReturnUserDTO(){
        User user = User.builder()
                .name("Laura")
                .lastname("Silva Santos")
                .cpf("920.529.090-44")
                .password("p9w2a4s!@")
                .expiresIn(LocalDate.now().plusDays(30))
                .build();

        UserDTO userDTO = userMapper.toDTO(user);

        Assertions.assertEquals(user.getName(), userDTO.name());
        Assertions.assertEquals(user.getLastname(), userDTO.lastname());
        Assertions.assertEquals(user.getCpf(), userDTO.cpf());
        Assertions.assertEquals(user.getPassword(), userDTO.password());
    }

    @Test
    public void whenToVoThenReturnUserVO(){
        User user = User.builder()
                .name("Laura")
                .lastname("Silva Santos")
                .cpf("920.529.090-44")
                .password("p9w2a4s!@")
                .expiresIn(LocalDate.now().plusDays(30))
                .build();

        UserVO userVO = userMapper.toVO(user);

        Assertions.assertEquals(user.getId(), userVO.id());
        Assertions.assertEquals(user.getName(), userVO.name());
        Assertions.assertEquals(user.getLastname(), userVO.lastname());
        Assertions.assertEquals(user.getExpiresIn(), userVO.expiresIn());
    }
}
