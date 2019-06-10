package com.maxwell.csafenet.model;

public class TrainingPassportModel {

    public String getEmployeename() {
        return employeename;
    }

    public void setEmployeename(String employeename) {
        this.employeename = employeename;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getTrainingTitle() {
        return trainingTitle;
    }

    public void setTrainingTitle(String trainingTitle) {
        this.trainingTitle = trainingTitle;
    }

    public String getTrainingProvider() {
        return trainingProvider;
    }

    public void setTrainingProvider(String trainingProvider) {
        this.trainingProvider = trainingProvider;
    }

    public String getDateDone() {
        return dateDone;
    }

    public void setDateDone(String dateDone) {
        this.dateDone = dateDone;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getTrainingEvidence() {
        return trainingEvidence;
    }

    public void setTrainingEvidence(String trainingEvidence) {
        this.trainingEvidence = trainingEvidence;
    }

    String employeename,employeeId,designation,trainingTitle,trainingProvider,dateDone,expiryDate,trainingEvidence;
}
