package com.example.repository;

import com.example.entity.ProductionRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductionRecordRepository extends JpaRepository<ProductionRecord, Long> {
}
