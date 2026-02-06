package com.example.controller;

import com.example.dto.ProductRawMaterialResponseDTO;
import com.example.entity.ProductRawMaterial;
import com.example.service.ProductRawMaterialService;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product-raw-materials")
public class ProductRawMaterialController {

    private final ProductRawMaterialService productRawMaterialService;

    public ProductRawMaterialController(ProductRawMaterialService productRawMaterialService) {
        this.productRawMaterialService = productRawMaterialService;
    }

    @Operation(summary = "vinculate a raw material to a product")
    @PostMapping("/{productId}/add")
    public ProductRawMaterialResponseDTO add(
            @PathVariable Long productId,
            @RequestBody ProductRawMaterialResponseDTO requestDTO) {

        return productRawMaterialService.addRawMaterialToProduct(
                productId, requestDTO.getRawMaterialId(), requestDTO.getRequiredQuantity());
    }

    @Operation(summary = "lista a raw material vinculated to a product")
    @GetMapping("/{productId}/raw-materials")
    public List<ProductRawMaterialResponseDTO> list(@PathVariable Long productId) {
        return productRawMaterialService.findByProduct(productId);
    }

    @DeleteMapping("/{rawMaterialId}")
    public void delete(@PathVariable Long productId,
                       @PathVariable Long rawMaterialId) {
        productRawMaterialService.removeRawMaterialFromProduct(productId, rawMaterialId);
    }

}
