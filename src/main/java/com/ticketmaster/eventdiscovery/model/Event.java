package com.ticketmaster.eventdiscovery.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import java.time.LocalTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {
    private String id;
    private String name;
    private String type;
    private LocalDate date;
    private LocalTime time;
    private String url;
    private Classification classification;
    private Venue venue;

    public Event() {}

    public Event(String id, String name, String type, LocalDate date, LocalTime time,
                 String url, Classification classification, Venue venue) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.date = date;
        this.time = time;
        this.url = url;
        this.classification = classification;
        this.venue = venue;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Classification getClassification() {
        return classification;
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }
}
