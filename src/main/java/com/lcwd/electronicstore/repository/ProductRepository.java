package com.lcwd.electronicstore.repository;

import com.lcwd.electronicstore.entity.Category;
import com.lcwd.electronicstore.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {


    Page<Product> findByTitleContaining(String subtitle, Pageable pageable);

    Page<Product> findByLiveTrue(Pageable pageable);

    Page<Product> findByCategory(Category category,Pageable pageable);


}
