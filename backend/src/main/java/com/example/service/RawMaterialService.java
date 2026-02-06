package com.example.service;


import com.example.dto.RawMaterialRequestDTO;
import com.example.dto.RawMaterialResponseDTO;
import com.example.entity.RawMaterial;
import com.example.repository.RawMaterialRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RawMaterialService {

    private final RawMaterialRepository rawMaterialRepository;

    public RawMaterialService(RawMaterialRepository rawMaterialRepository) {
        this.rawMaterialRepository = rawMaterialRepository;
    }


    public Page<RawMaterialResponseDTO> findAll(Pageable pageable) {
      return rawMaterialRepository.findAll(pageable).map(RawMaterialResponseDTO::new);

    }

    public RawMaterialResponseDTO findById(Long id) {
        RawMaterial rawMaterial= rawMaterialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Raw material not found"));
        return new RawMaterialResponseDTO(rawMaterial);
    }

    public RawMaterialResponseDTO create(RawMaterialRequestDTO rawMaterialRequestDTO) {
        RawMaterial rawMaterial= new RawMaterial();
        rawMaterial.setCode(rawMaterialRequestDTO.getCode());
        rawMaterial.setName(rawMaterialRequestDTO.getName());
        rawMaterial.setQuantity(rawMaterialRequestDTO.getQuantity());
        return new RawMaterialResponseDTO(rawMaterialRepository.save(rawMaterial));
    }

    public RawMaterialResponseDTO update(Long id, RawMaterialRequestDTO updated) {
        RawMaterial rawMaterial = rawMaterialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Raw material not found"));

        rawMaterial.setCode(updated.getCode());
        rawMaterial.setName(updated.getName());
        rawMaterial.setQuantity(updated.getQuantity());

        return new RawMaterialResponseDTO(rawMaterialRepository.save(rawMaterial));
    }

    public void delete(Long id) {
        rawMaterialRepository.deleteById(id);
    }

    @Transactional
    public RawMaterial adjustStock(Long rawMaterialId, int quantity) {
        RawMaterial rm = rawMaterialRepository.findById(rawMaterialId)
                .orElseThrow(() -> new IllegalArgumentException("Matéria-prima not found com ID: " + rawMaterialId));
        rm.setQuantity(rm.getQuantity() + quantity);
        return rawMaterialRepository.save(rm);
    }

}
