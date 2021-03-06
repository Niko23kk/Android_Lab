package com.smuzdev.lab_9.model;

import androidx.annotation.IntRange;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Events {

    @PrimaryKey
    private Integer id;
    private String time;
    private String place;
    private String eventType;
    private String prize;

    public Events(String eventType, String place, String time, String prize) {
        this.eventType = eventType;
        this.place = place;
        this.time = time;
        this.prize = prize;
    }

    //GETTERS
    public Integer getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public String getPlace() {
        return place;
    }

    public String getEventType() {
        return eventType;
    }

    public String getPrize() {
        return prize;
    }

    //SETTERS


    public void setId(Integer id) {
        this.id = id;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }
}
