package com.example.medicalsupplieswebsite.repository;

import com.example.medicalsupplieswebsite.dto.ProductHomeDto;
import com.example.medicalsupplieswebsite.dto.ProductPriceDto;
import com.example.medicalsupplieswebsite.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ICategoryRepository extends JpaRepository<Category,Long> {
    /**
     * VanNT
     */
    @Query(nativeQuery = true,
            value = "SELECT category_id, category_name " +
                    "FROM category ")
    List<Category> getCategory();
}
