package com.gtbfd.genmap.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;

@Entity
@Builder
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private float painelPreco;
    private float precoReferencia;
    private float precoBps;
    private int code;
    private String unit;

    public Item() {
    }

    public Item(Long id, String description, float painelPreco, float precoReferencia, float precoBps, int code, String unit) {
        this.id = id;
        this.description = description;
        this.painelPreco = painelPreco;
        this.precoReferencia = precoReferencia;
        this.precoBps = precoBps;
        this.code = code;
        this.unit = unit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPainelPreco() {
        return painelPreco;
    }

    public void setPainelPreco(float painelPreco) {
        this.painelPreco = painelPreco;
    }

    public float getPrecoReferencia() {
        return precoReferencia;
    }

    public void setPrecoReferencia(float precoReferencia) {
        this.precoReferencia = precoReferencia;
    }

    public float getPrecoBps() {
        return precoBps;
    }

    public void setPrecoBps(float precoBps) {
        this.precoBps = precoBps;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", painelPreco=" + painelPreco +
                ", precoReferencia=" + precoReferencia +
                ", precoBps=" + precoBps +
                ", code=" + code +
                ", unit='" + unit + '\'' +
                '}';
    }
}