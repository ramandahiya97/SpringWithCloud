package com.rest.webservices.inventory_management_system.repository;

import com.rest.webservices.inventory_management_system.model.Products;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductJPARepository extends JpaRepository<Products,Integer> {

    public List<Products> findByNameContainingIgnoreCase(String name);
    @Override
    Page<Products> findAll(@NotNull Pageable pageable);

    @Query("Select p from Products p where p.price> :minPrice and p.price =:category order By p.createdDate DESC")
    public List<Products> findByPriceGreaterThanAndSorted(@Param("minPrice")double minPrice
            ,@Param("category") String category);
    @Query(value = "SELECT * FROM products WHERE price > ?1 AND category = ?2 ORDER BY created_date DESC"
            , nativeQuery = true)
    List<Products> findProductsNative(double minPrice, String category);
    List<Products> findByIsDeletedFalse();

}
