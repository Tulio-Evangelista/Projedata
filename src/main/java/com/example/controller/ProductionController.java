package com.example.controller;


import com.example.dto.ProductionProductDTO;
import com.example.dto.ProductionRecordRequestDTO;
import com.example.dto.ProductionRequestDTO;
import com.example.dto.ProductionResponseDTO;
import com.example.entity.ProductionRecord;
import com.example.service.ProductionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.Map;

@RestController
@RequestMapping("/production")
public class ProductionController {

    private final ProductionService productionService;

    public ProductionController(ProductionService productionService) {

        this.productionService = productionService;
    }

    @Operation(summary = "calculate a production based on the raw material in inventory")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produção calculada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos")
    })


    @GetMapping("/calculate")
    public Page<ProductionProductDTO> calculateProduction(Pageable pageable) {
        return productionService.calculateProduction(pageable);
    }

    @PostMapping("/complete")
    public ProductionRecord completeProduction(@RequestBody ProductionRecordRequestDTO requestDTO) {
        return productionService.completeProduction(
                requestDTO.getProductId(),
                requestDTO.getQuantity()
        );
    }

    @GetMapping("/max-production")
    public Map<String, Integer> maxProduction() {
        return productionService.maxProductionPerProduct();
    }



}
