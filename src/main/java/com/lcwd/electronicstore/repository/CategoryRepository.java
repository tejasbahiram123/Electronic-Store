package com.lcwd.electronicstore.repository;

import com.lcwd.electronicstore.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category ,String> {


}
