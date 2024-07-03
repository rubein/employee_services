package com.example.rqchallenge.model;

import lombok.Data;

import java.util.List;

@Data
public class EmployeeResponse {
    private String status;
    private List<EmployeeDetail> data;
    private String message;
}
