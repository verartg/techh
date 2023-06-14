package com.example.techh.repositories;

import com.example.techh.models.Television;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TelevisionRepository extends JpaRepository<Television, Long> {
    List<Television> findAllTelevisionsByBrandEqualsIgnoreCase(String brand);
}
