package com.gtbfd.genmap.service;

import com.gtbfd.genmap.domain.Unit;
import com.gtbfd.genmap.domain.User;
import com.gtbfd.genmap.dto.UserDTO;
import com.gtbfd.genmap.mapper.UserMapper;
import com.gtbfd.genmap.repository.UnitRepository;
import com.gtbfd.genmap.repository.UserRepository;
import com.gtbfd.genmap.util.CnpjFormatter;
import com.gtbfd.genmap.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private CnpjFormatter cnpjFormatter;

    @Autowired
    private UserMapper userMapper;

    private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final String className = UserService.class.getName();

    public UserVO create(UserDTO userDTO){
        User user = userMapper.toMap(userDTO);
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Request to create user", className);
        if (Objects.nonNull(user)) {
            User createdUser = userRepository.save(user);
            UserVO userVO = userMapper.toVO(createdUser);
            LOGGER.info("[DEBUG]: Message = {} {}, Class = {}", "User created successfuly", userVO, className);
            return userVO;
        }
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "It wasn't possible to create a  new user", className);
        return null;
    }

    public UserVO findById(Long id){
        Optional<User> userFound = userRepository.findById(id);
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Request to find a user by ID", className);

        if (userFound.isPresent()){
            UserVO userVO = userMapper.toVO(userFound.orElse(null));
            LOGGER.info("[DEBUG]: Message = {} {}, Class = {}", "User found", userVO, className);
            return userVO;
        }
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "It wasn't possible to find a user with this ID", className);
        return null;
    }

    public UserVO findByCpf(String cpf){
        Optional<User> userFound = userRepository.findByCpf(cpf);
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Request to find a user by CPF", className);

        if (userFound.isPresent()){
            UserVO userVO = userMapper.toVO(userFound.orElse(null));
            LOGGER.info("[DEBUG]: Message = {} {}, Class = {}", "User found", userVO, className);
            return userVO;
        }
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "It wasn't possible to find a user with this CPF", className);
        return null;
    }

    public UserVO addUnit(String cpf, String cnpj){
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Request to add unit for user", className);
        Optional<User> userFound = userRepository.findByCpf(cpf);
        if (userFound.isPresent()){
            User userFoundEntity = userFound.get();
            LOGGER.info("[DEBUG]: Message = {} {}, Class = {}", "User found by CPF", userFoundEntity, className);

            Optional<Unit> unitFound = unitRepository.findByCnpj(cnpjFormatter.deformatCNPJ(cnpj));
            if (unitFound.isPresent()) {
                Unit unitFoundEntity = unitFound.get();
                LOGGER.info("[DEBUG]: Message = {} {}, Class = {}", "Unit found by CNPJ", unitFoundEntity, className);

                userFoundEntity.getUnits().add(unitFoundEntity);
                User userUpdated = userRepository.save(userFoundEntity);

                UserVO userVO = userMapper.toVO(userUpdated);
                LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Unit added", className);
                return userVO;
            }
        }
        return null;
    }

    public UserVO patchPassword(Long id, UserDTO userDTO){
        Optional<User> userFound = userRepository.findById(id);
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Request to patch user's password", className);

        if (userFound.isPresent()) {
            User user = userMapper.toMap(userDTO);
            if (Objects.nonNull(user.getPassword())) {
                userFound.get().setId(id);
                userFound.get().setPassword(user.getPassword());
                User userPatched = userRepository.save(userFound.get());

                UserVO userVO = userMapper.toVO(userPatched);
                LOGGER.info("[DEBUG]: Message = {} {}, Class = {}", "User patched", userVO, className);
                return userVO;
            }
            LOGGER.info("[DEBUG]: Message = {}, Class = {}", "User not found", className);
        }
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "It wasn't possible to patch user password", className);
        return null;
    }

    public UserVO authenticate(String cpf, String password){
        Optional<User> userAuthenticated = userRepository.findByCpfAndPassword(cpf, password);
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Request to authenticate user", className);

        if (userAuthenticated.isPresent() && isValid(userAuthenticated.get())){
            UserVO userVO = userMapper.toVO(userAuthenticated.orElseThrow());
            LOGGER.info("[DEBUG]: Message = {} {}, Class = {}", "Authenticated user", userVO, className);
            return userVO;
        }
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "It wasn't possible to authenticate", className);
        return null;
    }

    private boolean isValid(User user){
        if(user.getExpiresIn().isAfter(LocalDate.now())){
            LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Valid user", className);
            return true;
        }
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Invalid user", className);
        return false;
    }
}
