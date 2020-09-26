package com.fastwok.crawler.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="customer_fastwork")
public class CustomerFastwork {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String fw_id;
    private String customer;
}
