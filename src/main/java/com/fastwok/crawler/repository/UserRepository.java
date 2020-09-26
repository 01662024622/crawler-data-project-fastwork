package com.fastwok.crawler.repository;

import com.fastwok.crawler.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
