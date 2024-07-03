package com.example.rqchallenge.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
@Data
public class EmployeeResponseStatus {

    private String status;
    private List<EmployeeDetail> data;

//    @JsonProperty("status")
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    @JsonProperty("data")
//    public List<EmployeeDetail> getData() {
//        return data;
//    }
//
//    public void setData(List<EmployeeDetail> data) {
//        this.data = data;
//    }
}
