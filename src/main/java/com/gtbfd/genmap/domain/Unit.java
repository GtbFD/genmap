package com.gtbfd.genmap.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "tb_unit")
@SuperBuilder
public class Unit extends Company{
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
            },
            mappedBy = "units")
    private List<User> users;

    public Unit(){}

    public Unit(Long id, String nome, String cnpj, String logradouro, String bairro, String municipio, String uf, boolean isDeleted, List<User> users) {
        super(id, nome, cnpj, logradouro, bairro, municipio, uf, isDeleted);
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
