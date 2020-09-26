package com.fastwok.crawler.repository;

import com.fastwok.crawler.entities.B20Customer;
import com.fastwok.crawler.entities.SubTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubTaskRepository extends JpaRepository<SubTask, String> {
}
