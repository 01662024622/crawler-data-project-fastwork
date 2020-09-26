package com.fastwok.crawler.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name="tasks")
public class Task {
    @Id
    private String id;
    private String title;
    private String description;
    @Column(name="create_by")
    private String createBy;
    private String project;
    private String scenario;
    @Column(name="start_date")
    private String startDate;
    private String end_date;
    private String status;
    private String proccess;
    private Integer overdue;
    @Column(name="complete_date")
    private String completeDate;
    private Integer score;
    private String result;
    @Column(name="parent_id")
    private String parentId;


}
