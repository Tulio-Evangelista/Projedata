package com.example.controller;

import com.example.dto.ProductRawMaterialResponseDTO;
import com.example.entity.ProductRawMaterial;
import com.example.service.ProductRawMaterialService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product-raw-materials")
public class ProductRawMaterialController {

    private final ProductRawMaterialService productRawMaterialService;

    public ProductRawMaterialController(ProductRawMaterialService productRawMaterialService) {
        this.productRawMaterialService = productRawMaterialService;
    }

    @PostMapping
    public ProductRawMaterialResponseDTO add(
            @PathVariable Long productId,
            @RequestParam Long rawMaterialId,
            @RequestParam Integer requiredQuantity) {

        return productRawMaterialService.addRawMaterialToProduct(
                productId, rawMaterialId, requiredQuantity);
    }

    @GetMapping
    public List<ProductRawMaterialResponseDTO> list(@PathVariable Long productId) {
        return productRawMaterialService.findByProduct(productId);
    }

    @DeleteMapping("/{rawMaterialId}")
    public void delete(@PathVariable Long productId,
                       @PathVariable Long rawMaterialId) {
        productRawMaterialService.removeRawMaterialFromProduct(productId, rawMaterialId);
    }

}
