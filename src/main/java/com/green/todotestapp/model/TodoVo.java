package com.green.todotestapp.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TodoVo {
    private Long itodo;
    private String ctnt;
    private LocalDateTime createdAt;
    private String pic;
    private int finishYn;
    private LocalDateTime finishedAt;
}
