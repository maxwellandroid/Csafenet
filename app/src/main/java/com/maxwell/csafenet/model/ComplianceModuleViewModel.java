package com.maxwell.csafenet.model;

import java.util.List;

public class ComplianceModuleViewModel {
    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getRegTime() {
        return regTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSpecificLocation() {
        return specificLocation;
    }

    public void setSpecificLocation(String specificLocation) {
        this.specificLocation = specificLocation;
    }

    public List<AnswersDetailsModel> getAnswersDetailsModelList() {
        return answersDetailsModelList;
    }

    public void setAnswersDetailsModelList(List<AnswersDetailsModel> answersDetailsModelList) {
        this.answersDetailsModelList = answersDetailsModelList;
    }

    String regDate;
    String regTime;
    String location;
    String specificLocation;
   List<AnswersDetailsModel> answersDetailsModelList;
}
