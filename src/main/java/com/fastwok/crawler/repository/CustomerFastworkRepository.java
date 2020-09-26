package com.fastwok.crawler.repository;

import com.fastwok.crawler.entities.CustomerFastwork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface CustomerFastworkRepository extends JpaRepository<CustomerFastwork, Long> {
    @Query("SELECT cus FROM CustomerFastwork cus WHERE cus.fw_id=?1")
    CustomerFastwork getByIdFastwork(String id);
}
