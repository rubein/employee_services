package com.example.rqchallenge.employees;

import com.example.rqchallenge.model.DeleteResponse;
import com.example.rqchallenge.model.EmployeeDetail;
import com.example.rqchallenge.model.EmployeeResponseStatus;
import com.example.rqchallenge.service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class EmployeeController implements IEmployeeController{

    @Autowired
    EmployeeService service;

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public ResponseEntity<EmployeeResponseStatus> getAllEmployees() {
        System.out.println("Making call to the employee api");
        return service.getAllEmployee();
    }

    @Override
    public ResponseEntity<EmployeeResponseStatus> getEmployeesByNameSearch(String searchString)  {
        ResponseEntity<EmployeeResponseStatus> response = null;
        try {
            response = service.searchEmployeeByName(searchString);
        }catch(JsonProcessingException ex){
            log.error("Exception while parsing the json." + ex.getMessage());
        }
        return response;
    }

    @Override
    public ResponseEntity<EmployeeResponseStatus> getEmployeeById(String id) {
        ResponseEntity<EmployeeResponseStatus> response = null;
        try {
            response = service.searchEmployeeByID(id);
        }catch(JsonProcessingException ex) {
            log.error("Exception while parsing the json." + ex.getMessage());
        }
        return response;
    }

    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        ResponseEntity<Integer> response = null;
        try {
            response = service.highestEmpSalary();
        }catch(JsonProcessingException ex) {
            log.error("Exception while parsing the json." + ex.getMessage());
        }
        return response;
    }

    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        ResponseEntity<List<String>> response = null;
        try {
            response = service.top10Earners();
        }catch (JsonProcessingException ex){
            log.error("Exception while parsing the json." + ex.getMessage());
        }
        return response;
    }

    @Override
    public ResponseEntity<EmployeeDetail> createEmployee(Map<String, Object> employeeInput){
        return service.createEmployee(employeeInput);
    }

    @Override
    public ResponseEntity<DeleteResponse> deleteEmployeeById(String id){
        ResponseEntity<DeleteResponse> response = null;
        try {
            response = service.deleteEmployee(id);
        } catch (IOException ex) {
            log.error("IO Exception while reading the data" + ex.getMessage());
        }
        return response;
    }
}
