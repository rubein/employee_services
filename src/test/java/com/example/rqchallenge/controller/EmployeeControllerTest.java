package com.example.rqchallenge.controller;

import com.example.rqchallenge.employees.EmployeeController;
import com.example.rqchallenge.model.DeleteResponse;
import com.example.rqchallenge.model.EmployeeDetail;
import com.example.rqchallenge.model.EmployeeResponseStatus;
import com.example.rqchallenge.service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class EmployeeControllerTest {

    @Mock
    private EmployeeService service;

    @InjectMocks
    private EmployeeController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllEmployees() {
        EmployeeResponseStatus employeeResponseStatus = new EmployeeResponseStatus();
        when(service.getAllEmployee()).thenReturn(ResponseEntity.ok(employeeResponseStatus));

        ResponseEntity<EmployeeResponseStatus> response = controller.getAllEmployees();

        assertEquals(ResponseEntity.ok(employeeResponseStatus), response);
    }

    @Test
    void testGetEmployeesByNameSearch() throws JsonProcessingException {
        String searchString = "John Doe";
        EmployeeResponseStatus employeeResponseStatus = new EmployeeResponseStatus();
        when(service.searchEmployeeByName(searchString)).thenReturn(ResponseEntity.ok(employeeResponseStatus));

        ResponseEntity<EmployeeResponseStatus> response = controller.getEmployeesByNameSearch(searchString);

        assertEquals(ResponseEntity.ok(employeeResponseStatus), response);
    }

    @Test
    void testGetEmployeeById() throws JsonProcessingException {
        String id = "1";
        EmployeeResponseStatus employeeResponseStatus = new EmployeeResponseStatus();
        when(service.searchEmployeeByID(id)).thenReturn(ResponseEntity.ok(employeeResponseStatus));

        ResponseEntity<EmployeeResponseStatus> response = controller.getEmployeeById(id);

        assertEquals(ResponseEntity.ok(employeeResponseStatus), response);
    }

    @Test
    void testGetHighestSalaryOfEmployees() throws JsonProcessingException {
        Integer highestSalary = 100000;
        when(service.highestEmpSalary()).thenReturn(ResponseEntity.ok(highestSalary));

        ResponseEntity<Integer> response = controller.getHighestSalaryOfEmployees();

        assertEquals(ResponseEntity.ok(highestSalary), response);
    }

    @Test
    void testGetTopTenHighestEarningEmployeeNames() throws JsonProcessingException {
        List<String> topEarningEmployees = List.of("John Doe", "Jane Doe");
        when(service.top10Earners()).thenReturn(ResponseEntity.ok(topEarningEmployees));

        ResponseEntity<List<String>> response = controller.getTopTenHighestEarningEmployeeNames();

        assertEquals(ResponseEntity.ok(topEarningEmployees), response);
    }

    @Test
    void testCreateEmployee() throws JsonProcessingException {
        Map<String, Object> employeeInput = Map.of("name", "John Doe", "salary", "100000", "age", "30");
        EmployeeDetail employeeDetail = new EmployeeDetail();
        when(service.createEmployee(employeeInput)).thenReturn(ResponseEntity.ok(employeeDetail));

        ResponseEntity<EmployeeDetail> response = controller.createEmployee(employeeInput);

        assertEquals(ResponseEntity.ok(employeeDetail), response);
    }

    @Test
    void testDeleteEmployeeById() throws IOException {
        String id = "1";
        DeleteResponse deleteResponse = new DeleteResponse();
        deleteResponse.setStatus("success");
        deleteResponse.setMessage("Employee deleted successfully");
        when(service.deleteEmployee(id)).thenReturn(ResponseEntity.ok(deleteResponse));

        ResponseEntity<DeleteResponse> response = controller.deleteEmployeeById(id);

        assertEquals(ResponseEntity.ok(deleteResponse), response);
    }
}
