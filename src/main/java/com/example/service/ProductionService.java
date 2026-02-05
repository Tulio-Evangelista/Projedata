package com.example.service;

import com.example.dto.ProductionProductDTO;
import com.example.dto.ProductionRequestDTO;
import com.example.dto.ProductionResponseDTO;
import com.example.entity.Product;
import com.example.entity.ProductRawMaterial;
import com.example.entity.RawMaterial;
import com.example.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductionService {


    private final ProductRepository productRepository;

    public ProductionService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductionResponseDTO calculateProduction() {

        List<Product> products = productRepository.findAllByOrderByPriceDesc();


        Map<Long, Integer> stockMap = new HashMap<>();

        for (Product product : products) {
            for (ProductRawMaterial prm : product.getRawMaterials()) {
                RawMaterial rm = prm.getRawMaterial();
                stockMap.putIfAbsent(rm.getId(), rm.getQuantity());
            }
        }

        List<ProductionProductDTO> productionList = new ArrayList<>();

        // 3. Cálculo da produção
        for (Product product : products) {

            int maxQuantity = Integer.MAX_VALUE;

            for (ProductRawMaterial prm : product.getRawMaterials()) {
                Long rawMaterialId = prm.getRawMaterial().getId();
                int available = stockMap.getOrDefault(rawMaterialId, 0);
                int possible = available / prm.getRequiredQuantity();
                maxQuantity = Math.min(maxQuantity, possible);
            }

            if (maxQuantity > 0 && maxQuantity != Integer.MAX_VALUE) {

                // 4. Consumo do estoque simulado
                for (ProductRawMaterial prm : product.getRawMaterials()) {
                    Long rawMaterialId = prm.getRawMaterial().getId();
                    int used = prm.getRequiredQuantity() * maxQuantity;
                    stockMap.put(rawMaterialId,
                            stockMap.get(rawMaterialId) - used);
                }

                productionList.add(
                        new ProductionProductDTO(
                                product.getCode(),
                                product.getName(),
                                maxQuantity,
                                product.getPrice()
                        )
                );
            }
        }

        return new ProductionResponseDTO(productionList);
    }
}



