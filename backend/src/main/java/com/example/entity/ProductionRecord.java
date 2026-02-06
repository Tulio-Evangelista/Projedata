package com.example.entity;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ProductionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private Integer quantityProduced;

    private LocalDateTime productionDate;

    public ProductionRecord() {}

    public ProductionRecord(Product product, Integer quantityProduced, LocalDateTime productionDate) {
        this.product = product;
        this.quantityProduced = quantityProduced;
        this.productionDate = productionDate;
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getQuantityProduced() {
        return quantityProduced;
    }

    public LocalDateTime getProductionDate() {
        return productionDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantityProduced(Integer quantityProduced) {
        this.quantityProduced = quantityProduced;
    }

    public void setProductionDate(LocalDateTime productionDate) {
        this.productionDate = productionDate;
    }
}

