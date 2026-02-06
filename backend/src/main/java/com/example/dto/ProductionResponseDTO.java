package com.example.dto;

import java.math.BigDecimal;
import java.util.List;

public class ProductionResponseDTO {

    private List<ProductionProductDTO> products;
    private double totalValue;

    public ProductionResponseDTO(List<ProductionProductDTO> products) {
        this.products = products;
        this.totalValue = products.stream()
                .map(ProductionProductDTO::getTotalPrice)
                .reduce(0.0, Double::sum);
    }

    public List<ProductionProductDTO> getProducts() {
        return products;
    }

    public double getTotalValue() {
        return totalValue;
    }
}