package com.gtbfd.genmap.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@SuperBuilder
public class Supplier extends Company {

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private List<MapItem> maps;

    public Supplier() {
    }

    public Supplier(Long id, String nome, String cnpj, String logradouro, String bairro, String municipio, String uf, boolean isDeleted, List<MapItem> maps) {
        super(id, nome, cnpj, logradouro, bairro, municipio, uf, isDeleted);
        this.maps = maps;
    }

    public List<MapItem> getMaps() {
        return maps;
    }

    public void setMaps(List<MapItem> maps) {
        this.maps = maps;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                super.toString() +
                "}";
    }
}
