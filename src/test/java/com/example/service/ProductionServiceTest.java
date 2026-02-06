package com.example.service;

import com.example.dto.ProductionProductDTO;
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

import java.util.Arrays;
import java.util.Collections;
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
    void testCalculateProduction_WithAvailableStock() {

        RawMaterial rm1 = new RawMaterial();
        rm1.setId(1L);
        rm1.setName("RM1");
        rm1.setQuantity(100);

        RawMaterial rm2 = new RawMaterial();
        rm2.setId(2L);
        rm2.setName("RM2");
        rm2.setQuantity(50);


        ProductRawMaterial prm1 = new ProductRawMaterial();
        prm1.setId(1L);
        prm1.setRawMaterial(rm1);
        prm1.setRequiredQuantity(10);

        ProductRawMaterial prm2 = new ProductRawMaterial();
        prm2.setId(2L);
        prm2.setRawMaterial(rm2);
        prm2.setRequiredQuantity(5);


        Product product = new Product();
        product.setId(1L);
        product.setCode(101L);
        product.setName("Produto A");
        product.setPrice(25.0);
        product.setRawMaterials(Arrays.asList(prm1, prm2));


        prm1.setProduct(product);
        prm2.setProduct(product);


        when(productRepository.findAllByOrderByPriceDesc()).thenReturn(Collections.singletonList(product));


        ProductionResponseDTO response = productionService.calculateProduction();


        List<ProductionProductDTO> list = response.getProducts();
        assertThat(list).hasSize(1);
        assertThat(list.get(0).getProductName()).isEqualTo("Produto A");
        assertThat(list.get(0).getQuantity()).isEqualTo(10);
    }



}

