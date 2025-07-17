package com.rest.webservices.SpringWithCloud.repository;

import com.rest.webservices.SpringWithCloud.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {

}
