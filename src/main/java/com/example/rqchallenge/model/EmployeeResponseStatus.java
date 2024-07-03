package com.example.rqchallenge.model;

import lombok.Data;

import java.util.List;
@Data
public class EmployeeResponseStatus {

    private String status;
    private List<EmployeeDetail> data;

}
