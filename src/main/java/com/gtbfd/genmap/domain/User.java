package com.gtbfd.genmap.domain;

import jakarta.persistence.*;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "tb_user")
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastname;
    private String cpf;
    private String password;
    private LocalDate expiresIn;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "users_units",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "unit_id")})
    private List<Unit> units;

    public User() {
    }

    public User(Long id, String name, String lastname, String cpf, String password, LocalDate expiresIn, List<Unit> units) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.cpf = cpf;
        this.password = password;
        this.expiresIn = expiresIn;
        this.units = units;
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

    public List<Unit> getUnits() {
        return units;
    }

    public void setUnits(List<Unit> units) {
        this.units = units;
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
                ", units=" + units +
                '}';
    }
}
