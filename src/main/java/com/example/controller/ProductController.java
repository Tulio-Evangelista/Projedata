package com.example.controller;
import com.example.dto.ProductRequestDTO;
import com.example.dto.ProductResponseDTO;
import com.example.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {


    private final ProductService productService;



    public ProductController( ProductService productService) {
        this.productService = productService;

    }

        @GetMapping
        public Page<ProductResponseDTO> findAll(Pageable pageable) {
            return productService.findAll(pageable);
        }

        @GetMapping("/{id}")
        public ProductResponseDTO findById(@PathVariable Long id) {
            return productService.findById(id);
        }

        @PostMapping
        public ProductResponseDTO create(@RequestBody ProductRequestDTO product) {
            return productService.create(product);
        }

        @PutMapping("/{id}")
        public ProductResponseDTO update(@PathVariable Long id,
                @RequestBody ProductRequestDTO product) {
            return productService.update(id, product);
        }

        @DeleteMapping("/{id}")
        public void delete(@PathVariable Long id) {
            productService.delete(id);
        }

   }



