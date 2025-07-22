package com.rest.webservices.inventory_management_system.repository;

import com.rest.webservices.inventory_management_system.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {


}
