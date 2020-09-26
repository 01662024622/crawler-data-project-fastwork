package com.fastwok.crawler.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="customers")
public class Customer {
    @Id
    private Long Id;
    private String code;
    private String name_follow;
    @Column(nullable = true)
    private String name_main;
    @Column(nullable = true)
    private String name_business;
    @Column(nullable = true)
    private String address;
    @Column(nullable = true)
    private String main_group;
    @Column(nullable = true)
    private String categorize_customer;
    @Column(nullable = true)
    private String level_customer;
    @Column(nullable = true)
    private String supplies;
    @Column(nullable = true)
    private String supplies_phone_1;
    @Column(nullable = true)
    private String supplies_phone_2;
    @Column(nullable = true)
    private String supplies_phone_3;
    @Column(nullable = true)
    private String accountant_name;
    @Column(nullable = true)
    private String accountant_phone;
    @Column(nullable = true)
    private String boss_name;
    @Column(nullable = true)
    private String boss_phone;
    @Column(nullable = true)
    private String user_code;
    @Column(nullable = true)
    private String location;
    private Integer status;
    private String type;
    @Column(nullable = true)
    private String _id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated_at;

}
