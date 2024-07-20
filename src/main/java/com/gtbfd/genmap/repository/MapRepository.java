package com.gtbfd.genmap.repository;

import com.gtbfd.genmap.domain.Map;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MapRepository extends JpaRepository<Map, Long> {
}
