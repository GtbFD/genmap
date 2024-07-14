package com.gtbfd.genmap.repository;

import com.gtbfd.genmap.domain.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UnitRepository extends JpaRepository<Unit, Long> {
    Optional<Unit> findByCnpj(String cnpj);
}
