package com.gtbfd.genmap.service;

import com.gtbfd.genmap.domain.User;
import com.gtbfd.genmap.mapper.UserMapper;
import com.gtbfd.genmap.repository.UserRepository;
import com.gtbfd.genmap.vo.UserVO;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;


    private User user;

    @BeforeEach
    public void setUp(){
        user = User.builder()
                .name("Antônio")
                .lastname("Gomes Silva")
                .password("a9r8v2s9a!%#")
                .cpf("708.495,711-05")
                .expiresIn(LocalDate.now().plusDays(30))
                .build();
    }

    @Test
    public void whenCreateThenReturnNewUserCreated(){

        UserVO representation = new UserVO(1L, user.getName(), user.getLastname(), user.getExpiresIn());

        Mockito.when(userMapper.toMap(userMapper.toDTO(user))).thenReturn(user);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        Mockito.when(userMapper.toVO(user)).thenReturn(representation);

        UserVO createdUser = userService.create(userMapper.toDTO(user));

        Assertions.assertEquals(createdUser.id(), 1L);
        Assertions.assertEquals(createdUser.name(), user.getName());
        Assertions.assertEquals(createdUser.lastname(), user.getLastname());
        Assertions.assertEquals(createdUser.expiresIn(), user.getExpiresIn());
    }
}
