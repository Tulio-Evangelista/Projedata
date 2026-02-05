package com.example.repository;

import com.example.entity.ProductRawMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRawMaterialRepository extends JpaRepository<ProductRawMaterial, Long> {

    List<ProductRawMaterial> findByProductId(Long productId);


    void deleteByProductIdAndRawMaterialId(Long productId, Long rawMaterialId);
}
