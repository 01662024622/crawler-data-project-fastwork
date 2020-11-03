package com.fastwok.crawler.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name="sub_task")
public class SubTask {
    @Id
    private String id;
    @Column(length = 1000)
    private String title;
    @Column(name = "complete_date")
    private String completeDate;
    private String completeBy;
    @Column(name = "task_id")
    private String taskId;
}
