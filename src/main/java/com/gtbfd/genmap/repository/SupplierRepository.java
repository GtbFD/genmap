package com.gtbfd.genmap.repository;

import com.gtbfd.genmap.domain.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    Optional<Supplier> findByCnpj(String cnpj);
    List<Supplier> findByNomeContainingIgnoreCase(String nome);
}
