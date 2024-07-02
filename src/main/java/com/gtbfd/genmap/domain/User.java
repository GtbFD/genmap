package com.gtbfd.genmap.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastname;
    private String cpf;
    private String password;
    private LocalDate expiresIn;

    public User() {
    }

    public User(Long id, String name, String lastname, String cpf, String password, LocalDate expiresIn) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.cpf = cpf;
        this.password = password;
        this.expiresIn = expiresIn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(LocalDate expiresIn) {
        this.expiresIn = expiresIn;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", cpf='" + cpf + '\'' +
                ", password='" + password + '\'' +
                ", expiresIn=" + expiresIn +
                '}';
    }
}
