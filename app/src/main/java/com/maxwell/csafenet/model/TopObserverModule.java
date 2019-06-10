package com.maxwell.csafenet.model;

public class TopObserverModule {

    String userId;
    String name;
    String companyName;
    String totalObservations;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTotalObservations() {
        return totalObservations;
    }

    public void setTotalObservations(String totalObservations) {
        this.totalObservations = totalObservations;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    String image;
}
