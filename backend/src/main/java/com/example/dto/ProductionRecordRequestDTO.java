package com.example.dto;

public class ProductionRecordRequestDTO {

    private Long productId;
    private Integer quantity;

    public ProductionRecordRequestDTO() {}

    public ProductionRecordRequestDTO(Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }
}


