package com.example.dto;

import com.example.entity.ProductRawMaterial;

public class ProductRawMaterialResponseDTO {

    private Long rawMaterialId;
    private String rawMaterialCode;
    private String rawMaterialName;
    private Integer requiredQuantity;

    public ProductRawMaterialResponseDTO(ProductRawMaterial prm) {
        this.rawMaterialId = prm.getRawMaterial().getId();
        this.rawMaterialCode = prm.getRawMaterial().getCode();
        this.rawMaterialName = prm.getRawMaterial().getName();
        this.requiredQuantity = prm.getRequiredQuantity();
    }
    public ProductRawMaterialResponseDTO() {

    }


    public Long getRawMaterialId() {
        return rawMaterialId;
    }

    public String getRawMaterialCode() {
        return rawMaterialCode;
    }

    public String getRawMaterialName() {
        return rawMaterialName;
    }

    public Integer getRequiredQuantity() {
        return requiredQuantity;
    }
}
