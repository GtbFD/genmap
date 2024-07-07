package com.gtbfd.genmap.controller;

import com.gtbfd.genmap.domain.User;
import com.gtbfd.genmap.dto.UserDTO;
import com.gtbfd.genmap.service.UserService;
import com.gtbfd.genmap.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private static final String className = UserController.class.getName();

    @PostMapping
    public ResponseEntity<?> create(@RequestBody  UserDTO userDTO){
        UserVO createdUser = userService.create(userDTO);
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Request to create a new user", className);

        if (Objects.nonNull(createdUser)) {
            LOGGER.info("[DEBUG]: Message = {}, Class = {}", "User created successfuly", className);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        }
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "It wasn't possible to create a  new user", className);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
    }

    @PostMapping("/auth")
    public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO){
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Request to authenticate user", className);
        UserVO userAuthenticated = userService.authenticate(userDTO.cpf(), userDTO.password());

        if (Objects.nonNull(userAuthenticated)){
            LOGGER.info("[DEBUG]: Message = {} {}, Class = {}", "Authenticated user", userAuthenticated, className);
            return ResponseEntity.status(HttpStatus.OK).body(userAuthenticated);
        }
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "It wasn't possible to authenticate", className);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Request to find a user by ID", className);
        UserVO userFound = userService.findById(id);

        if (Objects.nonNull(userFound)){
            LOGGER.info("[DEBUG]: Message = {} {}, Class = {}", "User found", userFound, className);
            return ResponseEntity.status(HttpStatus.OK).body(userFound);
        }
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "It wasn't possible to find a user with this ID", className);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<?> findByCpf(@PathVariable String cpf){
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Request to find a user by CPF", className);
        UserVO userFound = userService.findByCpf(cpf);

        if (Objects.nonNull(userFound)){
            LOGGER.info("[DEBUG]: Message = {} {}, Class = {}", "User found", userFound, className);
            return ResponseEntity.status(HttpStatus.OK).body(userFound);
        }
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "It wasn't possible to find a user with this CPF", className);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchPassword(@PathVariable Long id, @RequestBody String password){
        LOGGER.info("[DEBUG]: Message = {}, Class = {}", "Request to patch user's password", className);
        UserDTO userDTO = new UserDTO("", "", "", password);
        UserVO userPatched = userService.patchPassword(id, userDTO);

        if (Objects.nonNull(userPatched)){
            LOGGER.info("[DEBUG]: Message = {} {}, Class = {}", "User patched", userPatched, className);
            return ResponseEntity.status(HttpStatus.OK).body(userPatched);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
    }
}
