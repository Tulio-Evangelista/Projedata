package com.example.dto;

import java.math.BigDecimal;

public class ProductionProductDTO {

    private Long productCode;
    private String productName;
    private Integer quantity;
    private double unitPrice;
    private double totalPrice;

    public ProductionProductDTO(Long productCode,
                                String productName,
                                Integer quantity,
                                double unitPrice) {
        this.productCode = productCode;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = unitPrice * quantity;
    }

    public Long getProductCode() {
        return productCode;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}

