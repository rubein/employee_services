package com.example.rqchallenge.employees;

import com.example.rqchallenge.model.DeleteResponse;
import com.example.rqchallenge.model.EmployeeDetail;
import com.example.rqchallenge.model.EmployeeResponseStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@RequestMapping("/api/v1")
public interface IEmployeeController {

    @GetMapping()
    ResponseEntity<EmployeeResponseStatus> getAllEmployees() throws IOException;

    @GetMapping("/search/{searchString}")
    ResponseEntity<EmployeeResponseStatus> getEmployeesByNameSearch(@PathVariable String searchString) throws JsonProcessingException;

    @GetMapping("/{id}")
    ResponseEntity<EmployeeResponseStatus> getEmployeeById(@PathVariable String id) throws JsonProcessingException;

    @GetMapping("/highestSalary")
    ResponseEntity<Integer> getHighestSalaryOfEmployees() throws JsonProcessingException;

    @GetMapping("/topTenHighestEarningEmployeeNames")
    ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() throws JsonProcessingException;

    @PostMapping()
    ResponseEntity<EmployeeDetail> createEmployee(@RequestBody Map<String, Object> employeeInput) throws JsonProcessingException;

    @DeleteMapping("/{id}")
    ResponseEntity<DeleteResponse> deleteEmployeeById(@PathVariable String id) throws IOException;

}
