package com.example.service;

import com.example.dto.ProductionResponseDTO;
import com.example.entity.Product;
import com.example.entity.ProductRawMaterial;
import com.example.entity.RawMaterial;
import com.example.repository.ProductRepository;
import com.example.repository.RawMaterialRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ProductionServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private RawMaterialRepository rawMaterialRepository;

    @InjectMocks
    private ProductionService productionService;


    @Test
    void shouldCalculateProductionWhenStockIsEnough() {

        RawMaterial steel = new RawMaterial(1L, "STEEL", "Steel", 100);

        Product car = new Product(1L, 1001L, "Car", 50000.0);
        ProductRawMaterial prm = new ProductRawMaterial(car, steel, 10);
        car.setRawMaterials(List.of(prm));

        when(productRepository.findAllByOrderByPriceDesc())
                .thenReturn(List.of(car));

        ProductionResponseDTO response =
                productionService.calculateProduction();

        assertThat(response.getProducts()).hasSize(1);
        assertThat(response.getProducts().get(0).getQuantity()).isEqualTo(10);
    }



    @Test
    void shouldNotProduceWhenStockIsInsufficient() {

        RawMaterial steel = new RawMaterial(1L, "STEEL", "Steel", 5);

        Product car = new Product(1L, 1001L, "Car", 50000.0);
        ProductRawMaterial prm = new ProductRawMaterial(car, steel, 10);
        car.setRawMaterials(List.of(prm));

        when(productRepository.findAllByOrderByPriceDesc())
                .thenReturn(List.of(car));

        ProductionResponseDTO response =
                productionService.calculateProduction();

        assertThat(response.getProducts()).isEmpty();
    }


}

