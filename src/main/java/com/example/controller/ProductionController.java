package com.example.controller;


import com.example.dto.ProductionProductDTO;
import com.example.dto.ProductionResponseDTO;
import com.example.service.ProductionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

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


}
