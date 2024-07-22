package com.gtbfd.genmap.repository;

import com.gtbfd.genmap.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByCode(int code);
}
