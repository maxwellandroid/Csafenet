package com.maxwell.csafenet.model;

public class QuestionsModel {

    String id;
    String title;

    public String getField_id() {
        return field_id;
    }

    public void setField_id(String field_id) {
        this.field_id = field_id;
    }

    String field_id;

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getSpecificLocationId() {
        return specificLocationId;
    }

    public void setSpecificLocationId(String specificLocationId) {
        this.specificLocationId = specificLocationId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getObservationCode() {
        return observationCode;
    }

    public void setObservationCode(String observationCode) {
        this.observationCode = observationCode;
    }

    String locationId;
    String specificLocationId;
    String answer;
    String observationCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
