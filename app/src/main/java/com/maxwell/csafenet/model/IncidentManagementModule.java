package com.maxwell.csafenet.model;

public class IncidentManagementModule {

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getActionBy() {
        return actionBy;
    }

    public void setActionBy(String actionBy) {
        this.actionBy = actionBy;
    }

    String date;
    String project;
    String location;
    String actionBy;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    String time;

    public String getDateOfIncidet() {
        return dateOfIncidet;
    }

    public void setDateOfIncidet(String dateOfIncidet) {
        this.dateOfIncidet = dateOfIncidet;
    }

    public String getDateReported() {
        return dateReported;
    }

    public void setDateReported(String dateReported) {
        this.dateReported = dateReported;
    }

    public String getReportedTo() {
        return reportedTo;
    }

    public void setReportedTo(String reportedTo) {
        this.reportedTo = reportedTo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocalTime() {
        return localTime;
    }

    public void setLocalTime(String localTime) {
        this.localTime = localTime;
    }

    public String getReportedTime() {
        return reportedTime;
    }

    public void setReportedTime(String reportedTime) {
        this.reportedTime = reportedTime;
    }

    public String getTeamLeader() {
        return teamLeader;
    }

    public void setTeamLeader(String teamLeader) {
        this.teamLeader = teamLeader;
    }

    public String getDepartmentManager() {
        return departmentManager;
    }

    public void setDepartmentManager(String departmentManager) {
        this.departmentManager = departmentManager;
    }

    public String getImplementingAuthority() {
        return implementingAuthority;
    }

    public void setImplementingAuthority(String implementingAuthority) {
        this.implementingAuthority = implementingAuthority;
    }

    public String getProposedConclusionIssueDate() {
        return proposedConclusionIssueDate;
    }

    public void setProposedConclusionIssueDate(String proposedConclusionIssueDate) {
        this.proposedConclusionIssueDate = proposedConclusionIssueDate;
    }

    public String getDateClosed() {
        return dateClosed;
    }

    public void setDateClosed(String dateClosed) {
        this.dateClosed = dateClosed;
    }

    String dateOfIncidet,dateReported,reportedTo,name,position,company,localTime,reportedTime,teamLeader,departmentManager,implementingAuthority,proposedConclusionIssueDate,dateClosed;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    String imagePath;
}
