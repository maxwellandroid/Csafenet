package com.maxwell.csafenet.model;

import java.io.Serializable;

public class MyObservationsModule implements Serializable {

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getObservatioType() {
        return observatioType;
    }

    public void setObservatioType(String observatioType) {
        this.observatioType = observatioType;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getRegCode() {
        return regCode;
    }

    public void setRegCode(String regCode) {
        this.regCode = regCode;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getEnterDate() {
        return enterDate;
    }

    public void setEnterDate(String enterDate) {
        this.enterDate = enterDate;
    }

    public String getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(String enterTime) {
        this.enterTime = enterTime;
    }

    public String getActionDesc() {
        return actionDesc;
    }

    public void setActionDesc(String actionDesc) {
        this.actionDesc = actionDesc;
    }

    public String getUnsafeCondition() {
        return unsafeCondition;
    }

    public void setUnsafeCondition(String unsafeCondition) {
        this.unsafeCondition = unsafeCondition;
    }

    public String getActionTaken() {
        return actionTaken;
    }

    public void setActionTaken(String actionTaken) {
        this.actionTaken = actionTaken;
    }

    public String getRecommended() {
        return recommended;
    }

    public void setRecommended(String recommended) {
        this.recommended = recommended;
    }

    public String getActionBy() {
        return actionBy;
    }

    public void setActionBy(String actionBy) {
        this.actionBy = actionBy;
    }

    public String getSafety() {
        return safety;
    }

    public void setSafety(String safety) {
        this.safety = safety;
    }

    public String getSafetyOthers() {
        return safetyOthers;
    }

    public void setSafetyOthers(String safetyOthers) {
        this.safetyOthers = safetyOthers;
    }

    public String getSafetyExplain() {
        return safetyExplain;
    }

    public void setSafetyExplain(String safetyExplain) {
        this.safetyExplain = safetyExplain;
    }

    public String getRisk() {
        return risk;
    }

    public void setRisk(String risk) {
        this.risk = risk;
    }

    public String getRiskOthers() {
        return riskOthers;
    }

    public void setRiskOthers(String riskOthers) {
        this.riskOthers = riskOthers;
    }

    public String getRiskExplain() {
        return riskExplain;
    }

    public void setRiskExplain(String riskExplain) {
        this.riskExplain = riskExplain;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    String employeeNumber;

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    String designation;

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    String userid;
    String userName;
    String locationName;
    String locationId;
    String companyName;
    String companyId;
    String observatioType;
    String observation;
    String regCode;
    String project;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    String projectName;
    String enterDate;
    String enterTime;
    String actionDesc;
    String unsafeCondition;
    String actionTaken;
    String recommended;
    String actionBy;

    public String getSafetyCategory() {
        return safetyCategory;
    }

    public void setSafetyCategory(String safetyCategory) {
        this.safetyCategory = safetyCategory;
    }

    String safetyCategory;

    public String getActionByName() {
        return actionByName;
    }

    public void setActionByName(String actionByName) {
        this.actionByName = actionByName;
    }

    String actionByName;
    String safety;
    String safetyOthers;
    String safetyExplain;
    String risk;
    String riskOthers;
    String riskExplain;
    String root;
    String regDate;

    public String getUploaded_image() {
        return uploaded_image;
    }

    public void setUploaded_image(String uploaded_image) {
        this.uploaded_image = uploaded_image;
    }

    String uploaded_image;

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    String image2;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String status;
}
