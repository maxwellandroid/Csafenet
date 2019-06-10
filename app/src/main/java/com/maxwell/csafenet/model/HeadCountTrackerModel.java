package com.maxwell.csafenet.model;

import java.io.Serializable;
import java.util.List;

public class HeadCountTrackerModel implements Serializable {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmergencyDetail() {
        return emergencyDetail;
    }

    public void setEmergencyDetail(String emergencyDetail) {
        this.emergencyDetail = emergencyDetail;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String name,designation,phoneNumber,emergencyDetail,project,status;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getHeadCountId() {
        return headCountId;
    }

    public void setHeadCountId(String headCountId) {
        this.headCountId = headCountId;
    }

    public String getEmergecyType() {
        return emergecyType;
    }

    public void setEmergecyType(String emergecyType) {
        this.emergecyType = emergecyType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    String projectId;
    String headCountId;
    String emergecyType;
    String date;

    public List<EmployeeStatusModel> getEmployeeStatusModelList() {
        return employeeStatusModelList;
    }

    public void setEmployeeStatusModelList(List<EmployeeStatusModel> employeeStatusModelList) {
        this.employeeStatusModelList = employeeStatusModelList;
    }

    List<EmployeeStatusModel> employeeStatusModelList;
}
