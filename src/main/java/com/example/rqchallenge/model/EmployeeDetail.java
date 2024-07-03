package com.example.rqchallenge.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Data
public class EmployeeDetail {

    @JSONField(name = "id")
    private String id;

    @JSONField(name = "employee_name")
    private String employee_name;

    @JSONField(name = "employee_salary")
    private String employee_salary;

    @JSONField(name = "employee_age")
    private String employee_age;

    @JSONField(name = "profile_image")
    private String profile_image;

    public EmployeeDetail(String id, String employee_name, String employee_salary, String employee_age, String profile_image) {
        this.id = id;
        this.employee_name = employee_name;
        this.employee_salary = employee_salary;
        this.employee_age = employee_age;
        this.profile_image = profile_image;
    }

    public EmployeeDetail(){}

//    public String getId() {
//        return id;
//    }
//
//    public String getEmployee_name() {
//        return employee_name;
//    }
//
//    public void setEmployeeName(String employee_name) {
//        this.employee_name = employee_name;
//    }
//
//    public String getEmployee_salary() {
//        return employee_salary;
//    }
//
//    public void setEmployeeSalary(String employee_salary) {
//        this.employee_salary = employee_salary;
//    }
//
//
//    public void setEmployee_age(String age) {
//        this.employee_age = age;
//    }
//
//    public String getProfile_image() {
//        return profile_image;
//    }
//
//    public void setProfile_image(String profile_image) {
//        this.profile_image = profile_image;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }

}


