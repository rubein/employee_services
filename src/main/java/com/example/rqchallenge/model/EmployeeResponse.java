package com.example.rqchallenge.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import lombok.Data;

import java.util.List;

@Data
public class EmployeeResponse {

    private String status;
    private List<EmployeeDetail> data;

    private String message;
//
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
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
}
