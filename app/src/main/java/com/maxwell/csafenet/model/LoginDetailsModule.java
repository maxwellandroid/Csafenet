package com.maxwell.csafenet.model;

public class LoginDetailsModule {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    String name;
    String employeeNumber;
    String designation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;
}
