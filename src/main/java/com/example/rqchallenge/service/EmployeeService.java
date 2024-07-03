package com.example.rqchallenge.service;

import com.example.rqchallenge.constants.Constants;
import com.example.rqchallenge.model.DeleteResponse;
import com.example.rqchallenge.model.EmployeeDetail;
import com.example.rqchallenge.model.EmployeeResponse;
import com.example.rqchallenge.model.EmployeeResponseStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    RestTemplate restTemplate;
    protected final Logger log = LoggerFactory.getLogger(getClass());
    private final AtomicInteger maxEmpId = new AtomicInteger(24);

    ObjectMapper mapper = new ObjectMapper();
    public ResponseEntity<EmployeeResponseStatus> getAllEmployee() {

        ResponseEntity<EmployeeResponse> response = restTemplate.exchange(
                URI.create(Constants.EMPLOYEE_EXAMPLE_PUBLIC_DOMAIN + Constants.EMPLOYEE_EXAMPLE_PUBLIC_URL_CONTEXT + Constants.EMPLOYEE_EXAMPLE_PUBLIC_GET_ALL),
                HttpMethod.GET,
                null,
                EmployeeResponse.class
        );

        if (response.getBody() != null && "success".equals(response.getBody().getStatus())) {
            EmployeeResponseStatus employeeResponseStatus = new EmployeeResponseStatus();
            employeeResponseStatus.setStatus(response.getBody().getStatus());
            employeeResponseStatus.setData(response.getBody().getData());
            return ResponseEntity.ok(employeeResponseStatus);
        } else {
            return ResponseEntity.status(response.getStatusCode()).body(createErrorResponse(Constants.RETRIEVAL_FAILURE));
        }
    }

    private EmployeeResponseStatus createErrorResponse(String message) {
        EmployeeResponseStatus errorResponse = new EmployeeResponseStatus();
        log.error(message);
        errorResponse.setStatus(Constants.ERROR);
        errorResponse.setData(new ArrayList<>());
        return errorResponse;
    }

    public ResponseEntity<EmployeeResponseStatus> searchEmployeeByName(String employee_name) throws JsonProcessingException {

        log.info("Looking for " + employee_name);

        String endpointUrl = Constants.EMPLOYEE_EXAMPLE_PUBLIC_DOMAIN +
                Constants.EMPLOYEE_EXAMPLE_PUBLIC_URL_CONTEXT +
                Constants.EMPLOYEE_EXAMPLE_PUBLIC_GET_ALL;

        ResponseEntity<String> response = restTemplate.getForEntity(URI.create(endpointUrl), String.class);
        log.info("response " + response.getBody());

        EmployeeResponse employeeResponse = mapper.readValue(response.getBody(), EmployeeResponse.class);

        List<EmployeeDetail> matchingEmployees = employeeResponse.getData().stream()
                .filter(employee -> employee_name.equals(employee.getEmployee_name()))
                .collect(Collectors.toList());

        EmployeeResponseStatus employeeResponseStatus = new EmployeeResponseStatus();

        if (matchingEmployees.isEmpty()) {
            employeeResponseStatus.setStatus("error");
            employeeResponseStatus.setData(matchingEmployees);
            return new ResponseEntity<>(employeeResponseStatus, HttpStatus.NOT_FOUND);
        } else {
            employeeResponseStatus.setStatus("success");
            employeeResponseStatus.setData(matchingEmployees);
            return new ResponseEntity<>(employeeResponseStatus, HttpStatus.OK);
        }
    }


    public ResponseEntity<EmployeeResponseStatus> searchEmployeeByID(String id) throws JsonProcessingException {

        log.info("Looking for employee with id " + id);

        String endpointUrl = Constants.EMPLOYEE_EXAMPLE_PUBLIC_DOMAIN +
                Constants.EMPLOYEE_EXAMPLE_PUBLIC_URL_CONTEXT +
                Constants.EMPLOYEE_EXAMPLE_PUBLIC_GET_ALL;

        ResponseEntity<String> response = restTemplate.getForEntity(URI.create(endpointUrl), String.class);
        log.info("response " + response.getBody());
        EmployeeResponse employeeResponse = mapper.readValue(response.getBody(), EmployeeResponse.class);

        EmployeeDetail matchingEmployee = employeeResponse.getData().stream()
                .filter(employee -> id.equals(employee.getId()))
                .findFirst()
                .orElse(null);

        EmployeeResponseStatus responseStatus = new EmployeeResponseStatus();

        if (matchingEmployee == null) {
            responseStatus.setStatus("error");
            responseStatus.setData(Collections.emptyList());
            return new ResponseEntity<>(responseStatus, HttpStatus.NOT_FOUND);
        } else {
            responseStatus.setStatus("success");
            responseStatus.setData(Collections.singletonList(matchingEmployee));
            return new ResponseEntity<>(responseStatus, HttpStatus.OK);
        }
    }


    public ResponseEntity<Integer> highestEmpSalary() throws JsonProcessingException {
        log.info("Getting highest Salary for Employee");
        String endpointUrl = Constants.EMPLOYEE_EXAMPLE_PUBLIC_DOMAIN +
                Constants.EMPLOYEE_EXAMPLE_PUBLIC_URL_CONTEXT +
                Constants.EMPLOYEE_EXAMPLE_PUBLIC_GET_ALL;

        ResponseEntity<String> response = restTemplate.getForEntity(URI.create(endpointUrl), String.class);

        EmployeeResponse employeeResponse = mapper.readValue(response.getBody(), EmployeeResponse.class);

        EmployeeDetail employeeWithHighestSalary = employeeResponse.getData().stream()
                .max(Comparator.comparing(employee -> Integer.parseInt(employee.getEmployee_salary())))
                .orElse(null);

        if (employeeWithHighestSalary == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(Integer.valueOf(employeeWithHighestSalary.getEmployee_salary()), HttpStatus.OK);
        }
    }


    public ResponseEntity<List<String>> top10Earners() throws JsonProcessingException {

        log.info("Looking for TOP 10 highest earner");

        String endpointUrl = Constants.EMPLOYEE_EXAMPLE_PUBLIC_DOMAIN +
                Constants.EMPLOYEE_EXAMPLE_PUBLIC_URL_CONTEXT +
                Constants.EMPLOYEE_EXAMPLE_PUBLIC_GET_ALL;

        ResponseEntity<String> response = restTemplate.getForEntity(URI.create(endpointUrl), String.class);
        EmployeeResponse employeeResponse = mapper.readValue(response.getBody(), EmployeeResponse.class);

        List<String> top10HighestEarningNames = employeeResponse.getData().stream()
                .sorted(Comparator.comparing(employee -> Integer.parseInt(employee.getEmployee_salary()), Comparator.reverseOrder()))
                .limit(10)
                .map(EmployeeDetail::getEmployee_name) // Extract only the employee names
                .collect(Collectors.toList());

        if (top10HighestEarningNames.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(top10HighestEarningNames, HttpStatus.OK);
        }
    }

    public ResponseEntity<EmployeeDetail> createEmployee(Map<String, Object> employeeInput) {

        log.info("Creating new Employee");
        String newEmpId = String.valueOf(maxEmpId.incrementAndGet());

        if (employeeInput.get(Constants.NAME) == null || employeeInput.get(Constants.SALARY) == null || employeeInput.get(Constants.AGE) == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        EmployeeDetail newEmployee = new EmployeeDetail();
        newEmployee.setEmployee_name((String) employeeInput.get(Constants.NAME));
        newEmployee.setEmployee_salary((String) employeeInput.get(Constants.SALARY));
        newEmployee.setEmployee_age((String) employeeInput.get(Constants.AGE));
        newEmployee.setId(newEmpId);
        return new ResponseEntity<>(newEmployee, HttpStatus.CREATED);
    }

    public ResponseEntity<DeleteResponse> deleteEmployee(String id) throws IOException {
        log.info("Deleting employee with ID " + id);
        DeleteResponse deleteResponse = new DeleteResponse();

        ResponseEntity<EmployeeResponse> response = restTemplate.exchange(
                URI.create(Constants.EMPLOYEE_EXAMPLE_PUBLIC_DOMAIN + Constants.EMPLOYEE_EXAMPLE_PUBLIC_URL_CONTEXT + Constants.EMPLOYEE_EXAMPLE_PUBLIC_GET_ALL),
                HttpMethod.GET,
                null,
                EmployeeResponse.class
        );

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            EmployeeResponse employeeResponse = response.getBody();
            List<EmployeeDetail> employeeList = new ArrayList<>((Collection) employeeResponse.getData());

            boolean removed = employeeList.removeIf(employee -> id.equalsIgnoreCase(employee.getId()));

            if (removed) {
                deleteResponse.setStatus("success");
                deleteResponse.setMessage("Employee with ID " + id + " deleted successfully");
                return new ResponseEntity<>(deleteResponse, HttpStatus.OK);
            } else {
                deleteResponse.setStatus("fail");
                deleteResponse.setMessage("Employee with ID " + id + " not found");
                return new ResponseEntity<>(deleteResponse, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>(deleteResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}