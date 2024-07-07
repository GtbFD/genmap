package com.gtbfd.genmap.repository;

import com.gtbfd.genmap.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByCpf(String cpf);
    Optional<User> findByCpfAndPassword(String cpf, String password);
}
