package com.fastwok.crawler.repository;

import com.fastwok.crawler.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, String> {
}
