package com.gtbfd.genmap.service;

import com.gtbfd.genmap.domain.User;
import com.gtbfd.genmap.dto.UserDTO;
import com.gtbfd.genmap.mapper.UserMapper;
import com.gtbfd.genmap.repository.UserRepository;
import com.gtbfd.genmap.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public UserVO create(UserDTO userDTO){
        User user = userMapper.toMap(userDTO);

        User createdUser = userRepository.save(user);

        return userMapper.toVO(createdUser);
    }
}
