package com.lcdw.electronic.store.repositories;

import com.lcdw.electronic.store.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;


public interface CategoryRepository extends JpaRepository<Category,String> {
}
