package com.fastwok.crawler.entities;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name="B20Customer")
public class B20Customer {
    @Id
    private Long Id;
    private String Code;
}
