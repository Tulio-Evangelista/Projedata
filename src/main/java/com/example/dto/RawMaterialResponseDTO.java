package com.example.dto;

import com.example.entity.RawMaterial;



public class RawMaterialResponseDTO {

    private Long id;
    private String code;
    private String name;
    private Integer quantity;

    public RawMaterialResponseDTO(RawMaterial rawMaterial) {
        this.id = rawMaterial.getId();
        this.code = rawMaterial.getCode();
        this.name = rawMaterial.getName();
        this.quantity = rawMaterial.getQuantity();
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }
}

