package com.example.service;

import com.example.dto.ProductionProductDTO;
import com.example.dto.ProductionResponseDTO;
import com.example.entity.Product;
import com.example.entity.ProductRawMaterial;
import com.example.entity.ProductionRecord;
import com.example.entity.RawMaterial;
import com.example.repository.ProductRepository;
import com.example.repository.ProductionRecordRepository;
import com.example.repository.RawMaterialRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class ProductionService {


    private final ProductRepository productRepository;
    private final RawMaterialRepository rawMaterialRepository;
    private final ProductionRecordRepository productionRecordRepository;


    public ProductionService(ProductRepository productRepository, RawMaterialRepository rawMaterialRepository, ProductionRecordRepository productionRecordRepository) {
        this.productRepository = productRepository;
        this.rawMaterialRepository = rawMaterialRepository;
        this.productionRecordRepository = productionRecordRepository;

    }


    public Page<ProductionProductDTO> calculateProduction(Pageable pageable) {

        List<Product> products = productRepository.findAllByOrderByPriceDesc();


        Map<Long, Integer> stockMap = new HashMap<>();

        for (Product product : products) {
            for (ProductRawMaterial prm : product.getRawMaterials()) {
                RawMaterial rm = prm.getRawMaterial();
                stockMap.putIfAbsent(rm.getId(), rm.getQuantity());
            }
        }

        List<ProductionProductDTO> productionList = new ArrayList<>();


        for (Product product : products) {

            int maxQuantity = Integer.MAX_VALUE;

            for (ProductRawMaterial prm : product.getRawMaterials()) {

                int required = prm.getRequiredQuantity();
                if (required <= 0) {
                    throw new IllegalStateException(
                            "Required quantity inválida para o produto " + product.getName()
                    );
                }

                Long rawMaterialId = prm.getRawMaterial().getId();
                int available = stockMap.getOrDefault(rawMaterialId, 0);
                int possible = available / prm.getRequiredQuantity();
                maxQuantity = Math.min(maxQuantity, possible);
            }


            if (maxQuantity > 0 && maxQuantity != Integer.MAX_VALUE) {


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



        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), productionList.size());
        return new PageImpl<>(productionList.subList(start, end), pageable, productionList.size());
    }

    @Transactional
    public ProductionRecord completeProduction(Long productId, int quantity) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));


        for (ProductRawMaterial prm : product.getRawMaterials()) {
            RawMaterial rm = prm.getRawMaterial();
            int needed = prm.getRequiredQuantity() * quantity;
            if (rm.getQuantity() < needed) {
                throw new RuntimeException(
                        "Matéria-prima insuficiente: " + rm.getName()
                );
            }
        }


        for (ProductRawMaterial prm : product.getRawMaterials()) {
            RawMaterial rm = prm.getRawMaterial();
            rm.setQuantity(rm.getQuantity() - prm.getRequiredQuantity() * quantity);
            rawMaterialRepository.save(rm); // Atualiza o estoque
        }


        ProductionRecord record = new ProductionRecord();
        record.setProduct(product);
        record.setQuantityProduced(quantity);
        record.setProductionDate(java.time.LocalDateTime.now());

        return productionRecordRepository.save(record);
    }

    public Map<String, Integer> maxProductionPerProduct() {
        List<Product> products = productRepository.findAll();

        Map<Long, Integer> stockMap = new HashMap<>();
        for (Product product : products) {
            for (ProductRawMaterial prm : product.getRawMaterials()) {
                stockMap.putIfAbsent(prm.getRawMaterial().getId(), prm.getRawMaterial().getQuantity());
            }
        }

        Map<String, Integer> result = new HashMap<>();

        for (Product product : products) {
            int maxQuantity = Integer.MAX_VALUE;

            for (ProductRawMaterial prm : product.getRawMaterials()) {
                Long rmId = prm.getRawMaterial().getId();
                int available = stockMap.getOrDefault(rmId, 0);
                int possible = available / prm.getRequiredQuantity();
                maxQuantity = Math.min(maxQuantity, possible);
            }

            result.put(product.getName(), maxQuantity == Integer.MAX_VALUE ? 0 : maxQuantity);
        }

        return result;
    }


}



