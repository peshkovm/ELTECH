package ru.eltech.stud.mapeshkov.shared;

import java.io.Serializable;

public class Formula_1RowReader implements Serializable {
    private static final long serialVersionUID = 1L;
    private String dateAndTime;
    private String stage;
    private String description;

    public Formula_1RowReader() {
    }

    public Formula_1RowReader(String dateAndTime, String stage, String description) {
        this.dateAndTime = dateAndTime;
        this.stage = stage;
        this.description = description;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public String getStage() {
        return stage;
    }

    public String getDescription() {
        return description;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}