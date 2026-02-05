package com.example.service;


import com.example.dto.ProductRawMaterialResponseDTO;
import com.example.entity.Product;
import com.example.entity.ProductRawMaterial;
import com.example.entity.RawMaterial;
import com.example.repository.ProductRawMaterialRepository;
import com.example.repository.ProductRepository;
import com.example.repository.RawMaterialRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductRawMaterialService {

    private final ProductRawMaterialRepository productRawMaterialRepository;
    private final ProductRepository productRepository;
    private final RawMaterialRepository rawMaterialRepository;;

    public ProductRawMaterialService(ProductRawMaterialRepository productRawMaterialRepository, ProductRepository productRepository, RawMaterialRepository rawMaterialRepository) {
        this.productRawMaterialRepository = productRawMaterialRepository;
        this.productRepository = productRepository;
        this.rawMaterialRepository = rawMaterialRepository;
    }


    public ProductRawMaterialResponseDTO addRawMaterialToProduct(
            Long productId,
            Long rawMaterialId,
            Integer requiredQuantity) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        RawMaterial rawMaterial = rawMaterialRepository.findById(rawMaterialId)
                .orElseThrow(() -> new RuntimeException("Raw material not found"));

        ProductRawMaterial prm = new ProductRawMaterial();
        prm.setProduct(product);
        prm.setRawMaterial(rawMaterial);
        prm.setRequiredQuantity(requiredQuantity);

        return new ProductRawMaterialResponseDTO(productRawMaterialRepository.save(prm));
    }

    public void removeRawMaterialFromProduct(Long productId, Long rawMaterialId) {
        productRawMaterialRepository
                .deleteByProductIdAndRawMaterialId(productId, rawMaterialId);
    }

    public List<ProductRawMaterialResponseDTO> findByProduct(Long productId) {
        return productRawMaterialRepository
                .findByProductId(productId)
                .stream()
                .map(ProductRawMaterialResponseDTO::new)
                .toList();
    }


}
