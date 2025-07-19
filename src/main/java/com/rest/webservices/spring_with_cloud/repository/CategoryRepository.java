package com.rest.webservices.spring_with_cloud.repository;

import com.rest.webservices.spring_with_cloud.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {

}
