package com.gtbfd.genmap.domain;

import jakarta.persistence.Entity;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
public class Supplier extends Company {

    public Supplier() {
    }

    public Supplier(Long id, String nome, String cnpj, String logradouro, String bairro, String municipio, String uf, boolean isDeleted) {
        super(id, nome, cnpj, logradouro, bairro, municipio, uf, isDeleted);
    }
}
