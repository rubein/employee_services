package com.example.rqchallenge.service;


import com.example.rqchallenge.model.DeleteResponse;
import com.example.rqchallenge.model.EmployeeDetail;
import com.example.rqchallenge.model.EmployeeResponse;
import com.example.rqchallenge.model.EmployeeResponseStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.doReturn;


class EmployeeServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private EmployeeService employeeService = new EmployeeService();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void testGetAllEmployee_success() {
        EmployeeResponse mockResponse = new EmployeeResponse();
        mockResponse.setStatus("success");
        mockResponse.setData(Arrays.asList(new EmployeeDetail("1", "John", "1000", "25", "")));

        doReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK)).when(restTemplate).exchange(any(URI.class), any(), any(), any(Class.class));
        ResponseEntity<EmployeeResponseStatus> response = employeeService.getAllEmployee();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("success", response.getBody().getStatus());
    }

    @Test
    void testSearchEmployeeByName() throws Exception {
        EmployeeResponse mockResponse = new EmployeeResponse();
        mockResponse.setStatus("success");
        mockResponse.setData(Arrays.asList(new EmployeeDetail("1", "Tiger Nixon", "1000", "25", "")));

        doReturn(new ResponseEntity<>(new ObjectMapper().writeValueAsString(mockResponse), HttpStatus.OK)).when(restTemplate)
                .getForEntity(any(URI.class), any(Class.class));

        ResponseEntity<EmployeeResponseStatus> response = employeeService.searchEmployeeByName("Tiger Nixon");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("success", response.getBody().getStatus());
    }

    @Test
    void testSearchEmployeeByID() throws Exception {
        EmployeeResponse mockResponse = new EmployeeResponse();
        mockResponse.setStatus("success");
        mockResponse.setData(Arrays.asList(new EmployeeDetail("1", "Tiger", "1000", "25", "")));

        doReturn(new ResponseEntity<>(new ObjectMapper().writeValueAsString(mockResponse), HttpStatus.OK)).when(restTemplate)
                .getForEntity(any(URI.class), any(Class.class));

        ResponseEntity<EmployeeResponseStatus> response = employeeService.searchEmployeeByID("1");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("success", response.getBody().getStatus());
    }

    @Test
    void testHighestEmpSalary() throws Exception {
        EmployeeResponse mockResponse = new EmployeeResponse();
        mockResponse.setStatus("success");
        mockResponse.setData(Arrays.asList(new EmployeeDetail("1", "John", "1000", "25", "")));

        doReturn(new ResponseEntity<>(new ObjectMapper().writeValueAsString(mockResponse), HttpStatus.OK)).when(restTemplate)
                .getForEntity(any(URI.class), any(Class.class));

        ResponseEntity<Integer> response = employeeService.highestEmpSalary();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1000, response.getBody());
    }

    @Test
    void testTop10Earners() throws JsonProcessingException {
        EmployeeResponse mockResponse = new EmployeeResponse();
        mockResponse.setStatus("success");
        mockResponse.setData(Arrays.asList(new EmployeeDetail("1", "Tiger", "1000", "25", ""),
                new EmployeeDetail("2", "Tiger", "1000", "25", ""),
                new EmployeeDetail("3", "Tiger", "1000", "25", ""),
                new EmployeeDetail("4", "Tiger", "1000", "25", ""),
                new EmployeeDetail("5", "Tiger", "1000", "25", ""),
                new EmployeeDetail("6", "Tiger", "1000", "25", ""),
                new EmployeeDetail("7", "Tiger", "1000", "25", ""),
                new EmployeeDetail("8", "Tiger", "1000", "25", ""),
                new EmployeeDetail("9", "Tiger", "1000", "25", ""),
                new EmployeeDetail("10", "Tiger", "1000", "25", "")
        ));

        doReturn(new ResponseEntity<>(new ObjectMapper().writeValueAsString(mockResponse), HttpStatus.OK)).when(restTemplate)
                .getForEntity(any(URI.class), any(Class.class));

        ResponseEntity<List<String>> response = employeeService.top10Earners();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(10, response.getBody().size());
        assertEquals("Tiger", response.getBody().get(0));
    }

    @Test
    void testCreateEmployee() {
        Map<String, Object> employeeInput = Map.of(
                "name", "John",
                "salary", "1000",
                "age", "25"
        );

        ResponseEntity<EmployeeDetail> response = employeeService.createEmployee(employeeInput);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("John", response.getBody().getEmployee_name());
    }

    @Test
    void testCreateEmployee_invalidInput_fail() {
        Map<String, Object> employeeInput = Map.of(
                "name", "John"
        );

        ResponseEntity<EmployeeDetail> response = employeeService.createEmployee(employeeInput);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testDeleteEmployee() throws IOException {
        EmployeeResponse mockResponse = new EmployeeResponse();
        mockResponse.setStatus("success");
        mockResponse.setData(Arrays.asList(new EmployeeDetail("1", "John", "1000", "25", "")));

        doReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK)).when(restTemplate).exchange(any(URI.class), any(), any(), any(Class.class));

        ResponseEntity<DeleteResponse> response = employeeService.deleteEmployee("1");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("success", response.getBody().getStatus());
    }


    @Test
    void testDeleteEmployee_notFound_fail() throws IOException {
        EmployeeResponse mockResponse = new EmployeeResponse();
        mockResponse.setStatus("success");
        mockResponse.setData(Arrays.asList(new EmployeeDetail("1", "John", "1000", "25", "")));

        doReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK)).when(restTemplate).exchange(any(URI.class), any(), any(), any(Class.class));

        ResponseEntity<DeleteResponse> response = employeeService.deleteEmployee("30");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("fail", response.getBody().getStatus());
    }
}
