package com.maxwell.csafenet.model;

public class CEVSModel {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmergencyCase() {
        return emergencyCase;
    }

    public void setEmergencyCase(String emergencyCase) {
        this.emergencyCase = emergencyCase;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    String id,emergencyCase,project;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    String projectId;
}
