package com.gtbfd.genmap.domain;

import jakarta.persistence.*;
import lombok.Builder;

@Entity
@Builder
public class MapItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Map map;
    @ManyToOne
    private Item item;
    @ManyToOne
    private Supplier supplier;
    private float quantity;
    private float price;
    public MapItem() {
    }

    public MapItem(Long id, Map map, Item item, Supplier supplier, float quantity, float price) {
        this.id = id;
        this.map = map;
        this.item = item;
        this.supplier = supplier;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "MapItem{" +
                "id=" + id +
                ", map=" + map +
                ", item=" + item +
                ", supplier=" + supplier +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
