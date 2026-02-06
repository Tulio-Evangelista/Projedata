package com.example.service;

import com.example.dto.ProductRequestDTO;
import com.example.dto.ProductResponseDTO;
import com.example.entity.Product;
import com.example.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public Page<ProductResponseDTO> findAll(Pageable pageable) {
        return productRepository.findAll(pageable).map(ProductResponseDTO::new);

    }

    public ProductResponseDTO findById(Long id) {
        Product product= productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return new ProductResponseDTO(product);
    }

    public ProductResponseDTO create(ProductRequestDTO productRequestDTO) {
        Product product = new Product();
        product.setCode(productRequestDTO.getCode());
        product.setName(productRequestDTO.getName());
        product.setPrice(productRequestDTO.getPrice());
        return new ProductResponseDTO(productRepository.save(product));
    }

    public ProductResponseDTO update(Long id, ProductRequestDTO updatedProduct) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setCode(updatedProduct.getCode());
        product.setName(updatedProduct.getName());
        product.setPrice(updatedProduct.getPrice());

        return new ProductResponseDTO(productRepository.save(product));
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }

}
