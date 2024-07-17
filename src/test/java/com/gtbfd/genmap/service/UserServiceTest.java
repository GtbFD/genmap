package com.gtbfd.genmap.service;

import com.gtbfd.genmap.domain.Unit;
import com.gtbfd.genmap.domain.User;
import com.gtbfd.genmap.dto.UserDTO;
import com.gtbfd.genmap.mapper.UserMapper;
import com.gtbfd.genmap.repository.UnitRepository;
import com.gtbfd.genmap.repository.UserRepository;
import com.gtbfd.genmap.util.CnpjFormatter;
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
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UnitRepository unitRepository;

    @Mock
    private CnpjFormatter cnpjFormatter;
    @InjectMocks
    private UserService userService;

    private Unit unit;

    private User user;
    private UserMapper userMapperTest;

    @BeforeEach
    public void setUp() {

        userMapperTest = new UserMapper();

        unit = Unit.builder()
                .id(1L)
                .nome("SHF Comércio e Serviços")
                .cnpj("00.000.000/0000-00")
                .logradouro("Rua 13 de Maio")
                .bairro("Centro")
                .municipio("Cajazeiras")
                .uf("PB")
                .build();

        user = User.builder()
                .id(1L)
                .name("Antônio")
                .lastname("Gomes Silva")
                .password("a9r8v2s9a!%#")
                .cpf("708.495,711-05")
                .units(new ArrayList<>())
                .expiresIn(LocalDate.now().plusDays(30))
                .build();
    }

    @Test
    public void whenCreateThenReturnNewUserCreated() {

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

    @Test
    public void whenUserNullThenCantCreateUser() {

        UserDTO userDTO = null;
        Mockito.when(userMapper.toMap(userDTO)).thenReturn(null);

        UserVO createdUser = userService.create(userDTO);

        Assertions.assertNull(createdUser);
    }

    @Test
    public void whenFindByIdThenReturnUser() {
        Long id = 1L;
        UserVO representation = new UserVO(1L, user.getName(), user.getLastname(), user.getExpiresIn());

        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));
        Mockito.when(userMapper.toVO(user)).thenReturn(representation);

        UserVO foundUser = userService.findById(id);

        Assertions.assertNotNull(foundUser);
        Mockito.verify(userRepository, Mockito.times(1)).findById(id);
        Mockito.verify(userMapper, Mockito.times(1)).toVO(user);
    }

    @Test
    public void whenFindByIdWhenIncorrectIdThenReturn() {
        Long id = 809818L;
        UserVO representation = null;

        Mockito.when(userRepository.findById(id)).thenReturn(Optional.empty());

        UserVO userFound = userService.findById(id);

        Mockito.verify(userRepository, Mockito.times(1)).findById(id);
        Assertions.assertNull(userFound);
    }

    @Test
    public void whenFindByCpfThenReturnUser() {
        String cpf = user.getCpf();
        UserVO representation = new UserVO(1L, user.getName(), user.getLastname(), user.getExpiresIn());

        Mockito.when(userRepository.findByCpf(cpf)).thenReturn(Optional.of(user));
        Mockito.when(userMapper.toVO(user)).thenReturn(representation);

        UserVO userFound = userService.findByCpf(cpf);

        Assertions.assertNotNull(userFound);
        Mockito.verify(userRepository, Mockito.times(1)).findByCpf(cpf);
        Mockito.verify(userMapper, Mockito.times(1)).toVO(user);
    }

    @Test
    public void whenFindByCpfWithInexistentCpfThenReturnNull() {
        String cpf = "10365741028";

        Mockito.when(userRepository.findByCpf(cpf)).thenReturn(Optional.empty());

        UserVO userFound = userService.findByCpf(cpf);

        Mockito.verify(userRepository, Mockito.times(1)).findByCpf(cpf);
        Assertions.assertNull(userFound);
    }

    @Test
    public void whenPatchPasswordThenUpdate() {
        user.setId(1L);
        UserDTO userDTO = new UserDTO(user.getName(), user.getLastname(), user.getCpf(), user.getPassword());
        String newPassword = "NewPassword";
        UserVO userVO = new UserVO(user.getId(), user.getName(), user.getLastname(), user.getExpiresIn());

        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Mockito.when(userMapper.toMap(userDTO)).thenReturn(user);
        user.setPassword(newPassword);

        Mockito.when(userRepository.save(user)).thenReturn(user);
        Mockito.when(userMapper.toVO(user)).thenReturn(userVO);

        UserVO userPatched = userService.patchPassword(user.getId(), userDTO);

        Assertions.assertNotNull(userPatched);
        Mockito.verify(userRepository, Mockito.times(1)).findById(user.getId());
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    public void whenPatchPasswordWithInvalidIdThenReturnNull() {
        Long id = 1500L;
        UserDTO userDTO = new UserDTO(user.getName(), user.getLastname(), user.getCpf(), user.getPassword());

        Mockito.when(userRepository.findById(id)).thenReturn(Optional.empty());

        UserVO userPatched = userService.patchPassword(id, userDTO);

        Assertions.assertNull(userPatched);
        Mockito.verify(userRepository, Mockito.times(1)).findById(id);
        Mockito.verifyNoInteractions(userMapper);
    }

    @Test
    public void whenAuthenticateUserThenReturnTrue() {

        UserVO representation = new UserVO(1L, user.getName(), user.getLastname(), user.getExpiresIn());

        Mockito.when(userRepository.findByCpfAndPassword(user.getCpf(), user.getPassword())).thenReturn(Optional.of(user));
        Mockito.when(userMapper.toVO(user)).thenReturn(representation);

        UserVO userAuthenticated = userService.authenticate(user.getCpf(), user.getPassword());

        Assertions.assertNotNull(userAuthenticated);
    }

    @Test
    public void whenUnauthenticatedUserThenReturnNull() {
        Mockito.when(userRepository.findByCpfAndPassword("80536512079", "user1473@#")).thenReturn(Optional.empty());

        UserVO userAuthenticated = userService.authenticate("80536512079", "user1473@#");

        Assertions.assertNull(userAuthenticated);
    }

    @Test
    public void whenAddUnitThenSuccess() {
        String cpf = "00000000000";
        String cnpj = "00.000.000/0000-00";

        UserVO userVO = userMapperTest.toVO(user);

        Mockito.when(userRepository.findByCpf(cpf)).thenReturn(Optional.of(user));
        Mockito.when(cnpjFormatter.deformatCNPJ(cnpj)).thenReturn("00000000000000");
        Mockito.when(unitRepository.findByCnpj("00000000000000")).thenReturn(Optional.of(unit));
        Mockito.when(userRepository.save(user)).thenReturn(user);
        Mockito.when(userMapper.toVO(user)).thenReturn(userVO);

        UserVO response = userService.addUnit(cpf, cnpj);

        System.out.println(response);
    }
}
