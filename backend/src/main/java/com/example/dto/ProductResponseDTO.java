package com.example.dto;

import com.example.entity.Product;

public class ProductResponseDTO {


        private Long id;
        private Long code;
        private String name;
        private double price;

        public ProductResponseDTO(Product product) {
            this.id = product.getId();
            this.code = product.getCode();
            this.name = product.getName();
            this.price = product.getPrice();
        }


    }


