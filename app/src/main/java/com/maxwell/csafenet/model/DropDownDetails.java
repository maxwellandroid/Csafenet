package com.maxwell.csafenet.model;

public class DropDownDetails {

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

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getProjectsId() {
        return projectsId;
    }

    public void setProjectsId(String projectsId) {
        this.projectsId = projectsId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getSafetyCategory() {
        return safetyCategory;
    }

    public void setSafetyCategory(String safetyCategory) {
        this.safetyCategory = safetyCategory;
    }

    public String getSafetyCategoryId() {
        return safetyCategoryId;
    }

    public void setSafetyCategoryId(String safetyCategoryId) {
        this.safetyCategoryId = safetyCategoryId;
    }

    public String getActionBy() {
        return actionBy;
    }

    public void setActionBy(String actionBy) {
        this.actionBy = actionBy;
    }

    public String getActionById() {
        return actionById;
    }

    public void setActionById(String actionById) {
        this.actionById = actionById;
    }

    String companyName;
    String companyId;
    String project;
    String projectsId;
    String location;
    String locationId;
    String safetyCategory;
    String safetyCategoryId;
    String actionBy;
    String actionById;

    public String getRiskCategory() {
        return riskCategory;
    }

    public void setRiskCategory(String riskCategory) {
        this.riskCategory = riskCategory;
    }

    public String getRiskCategoryId() {
        return riskCategoryId;
    }

    public void setRiskCategoryId(String riskCategoryId) {
        this.riskCategoryId = riskCategoryId;
    }

    public String getRootCause() {
        return rootCause;
    }

    public void setRootCause(String rootCause) {
        this.rootCause = rootCause;
    }

    public String getRootCauseId() {
        return rootCauseId;
    }

    public void setRootCauseId(String rootCauseId) {
        this.rootCauseId = rootCauseId;
    }

    String riskCategory;
    String riskCategoryId;
    String rootCause;
    String rootCauseId;

    public String getSpecificLocation() {
        return specificLocation;
    }

    public void setSpecificLocation(String specificLocation) {
        this.specificLocation = specificLocation;
    }

    public String getSpecificLocationId() {
        return specificLocationId;
    }

    public void setSpecificLocationId(String specificLocationId) {
        this.specificLocationId = specificLocationId;
    }

    String specificLocation,specificLocationId;
}
