package com.example.dto;

import com.example.entity.ProductionRecord;

import java.time.LocalDateTime;

public class ProductionRecordResponseDTO {


    private Long id;
    private Long productCode;
    private String productName;
    private Integer quantityProduced;
    private LocalDateTime productionDate;

    public ProductionRecordResponseDTO(ProductionRecord record) {
        this.id = record.getId();
        this.productCode = record.getProduct().getCode();
        this.productName = record.getProduct().getName();
        this.quantityProduced = record.getQuantityProduced();
        this.productionDate = record.getProductionDate();
    }

    public Long getId() {
        return id;
    }

    public Long getProductCode() {
        return productCode;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getQuantityProduced() {
        return quantityProduced;
    }

    public LocalDateTime getProductionDate() {
        return productionDate;
    }
}
