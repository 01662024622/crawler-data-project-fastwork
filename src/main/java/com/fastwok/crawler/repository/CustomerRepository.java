package com.fastwok.crawler.repository;

import com.fastwok.crawler.entities.B20Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CustomerRepository extends JpaRepository<B20Customer, Long> {

    @Query("SELECT cus FROM B20Customer cus WHERE cus.Code LIKE ?1%")
    List<B20Customer> getByIdCode( String code);
}
