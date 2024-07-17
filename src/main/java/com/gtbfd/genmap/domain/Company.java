package com.gtbfd.genmap.domain;

import com.gtbfd.genmap.util.CnpjFormatter;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Builder;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@SuperBuilder
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cnpj;
    private String logradouro;
    private String bairro;
    private String municipio;
    private String uf;

    public Company() {
    }

    public Company(Long id, String nome, String cnpj, String logradouro, String bairro, String municipio, String uf) {
        this.id = id;
        this.nome = nome;
        this.cnpj = cnpj;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.municipio = municipio;
        this.uf = uf;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = new CnpjFormatter().deformatCNPJ(cnpj);
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", logradouro='" + logradouro + '\'' +
                ", bairro='" + bairro + '\'' +
                ", municipio='" + municipio + '\'' +
                ", uf='" + uf + '\'' +
                '}';
    }
}
