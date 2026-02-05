package com.example.controller;


import com.example.dto.ProductionResponseDTO;
import com.example.service.ProductionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/production")
public class ProductionController {

    private final ProductionService productionService;

    public ProductionController(ProductionService productionService) {
        this.productionService = productionService;
    }

    @GetMapping("/suggestion")
    public ProductionResponseDTO getProductionSuggestion() {
        return productionService.calculateProduction();
    }


}
