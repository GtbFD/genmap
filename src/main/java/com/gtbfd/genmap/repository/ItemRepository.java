package com.gtbfd.genmap.repository;

import com.gtbfd.genmap.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
