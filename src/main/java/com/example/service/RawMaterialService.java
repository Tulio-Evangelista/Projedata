package com.example.service;


import com.example.dto.RawMaterialRequestDTO;
import com.example.dto.RawMaterialResponseDTO;
import com.example.entity.RawMaterial;
import com.example.repository.RawMaterialRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RawMaterialService {

    private final RawMaterialRepository rawMaterialRepository;

    public RawMaterialService(RawMaterialRepository rawMaterialRepository) {
        this.rawMaterialRepository = rawMaterialRepository;
    }


    public List<RawMaterialResponseDTO> findAll() {
      return rawMaterialRepository.findAll().stream()
                .map(RawMaterialResponseDTO::new)
                .toList();
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


}
