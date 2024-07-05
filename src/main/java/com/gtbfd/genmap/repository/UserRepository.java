package com.gtbfd.genmap.repository;

import com.gtbfd.genmap.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
