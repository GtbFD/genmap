package com.gtbfd.genmap.service;

import com.gtbfd.genmap.domain.User;
import com.gtbfd.genmap.dto.UserDTO;
import com.gtbfd.genmap.mapper.UserMapper;
import com.gtbfd.genmap.repository.UserRepository;
import com.gtbfd.genmap.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public UserVO create(UserDTO userDTO){
        User user = userMapper.toMap(userDTO);

        if (Objects.nonNull(user)) {
            User createdUser = userRepository.save(user);
            return userMapper.toVO(createdUser);
        }
        return null;
    }

    public UserVO findById(Long id){
        Optional<User> userFound = userRepository.findById(id);

        if (userFound.isPresent()){
            return userMapper.toVO(userFound.orElse(null));
        }
        return null;
    }

    public UserVO findByCpf(String cpf){
        Optional<User> userFound = userRepository.findByCpf(cpf);

        if (userFound.isPresent()){
            return userMapper.toVO(userFound.orElse(null));
        }
        return null;
    }

    public UserVO patchPassword(Long id, UserDTO userDTO){
        Optional<User> userFound = userRepository.findById(id);

        if (userFound.isPresent()) {
            User user = userMapper.toMap(userDTO);
            if (Objects.nonNull(user.getPassword())) {
                userFound.get().setId(id);
                userFound.get().setPassword(user.getPassword());
                User userPatched = userRepository.save(userFound.get());

                return userMapper.toVO(userPatched);
            }
        }
        return null;
    }

    public UserVO authenticate(String cpf, String password){
        Optional<User> userAuthenticated = userRepository.findByCpfAndPassword(cpf, password);

        if (userAuthenticated.isPresent() && checkValidation(userAuthenticated.get())){
            return userMapper.toVO(userAuthenticated.orElse(null));
        }
        return null;
    }

    private boolean checkValidation(User user){
        return user.getExpiresIn().isAfter(LocalDate.now());
    }
}
