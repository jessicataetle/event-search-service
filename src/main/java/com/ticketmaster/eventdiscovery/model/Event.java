package com.ticketmaster.eventdiscovery.model;

import com.ticketmaster.eventdiscovery.constant.ApiFieldConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {
    private String id;
    private String name;
    private String type;
    private LocalDate date;
    private String url;
    private Classification classification;
    private Venue venue;

    @JsonProperty(ApiFieldConstants.DATES)
    private void setDates(JsonNode dates) {
        if (dates != null && dates.has(ApiFieldConstants.START)) {
            JsonNode start = dates.get(ApiFieldConstants.START);
            if (start.has(ApiFieldConstants.LOCAL_DATE)) {
                this.date = LocalDate.parse(start.get(ApiFieldConstants.LOCAL_DATE).asText());
            }
        }
    }

    @JsonProperty(ApiFieldConstants.CLASSIFICATIONS)
    private void setClassifications(JsonNode[] classifications) {
        if (classifications != null && classifications.length > 0) {
            this.classification = new Classification();
            JsonNode firstClassification = classifications[0];
            if (firstClassification.has(ApiFieldConstants.GENRE)) {
                Genre genre = new Genre();
                genre.setId(firstClassification.get(ApiFieldConstants.GENRE).get(ApiFieldConstants.ID).asText());
                genre.setName(firstClassification.get(ApiFieldConstants.GENRE).get(ApiFieldConstants.NAME).asText());
                this.classification.setGenre(genre);
            }
            if (firstClassification.has(ApiFieldConstants.SUB_GENRE)) {
                SubGenre subGenre = new SubGenre();
                subGenre.setId(firstClassification.get(ApiFieldConstants.SUB_GENRE).get(ApiFieldConstants.ID).asText());
                subGenre.setName(firstClassification.get(ApiFieldConstants.SUB_GENRE).get(ApiFieldConstants.NAME).asText());
                this.classification.setSubGenre(subGenre);
            }
        }
    }

    @JsonProperty(ApiFieldConstants.EMBEDDED)
    private void setEmbedded(JsonNode embedded) {
        if (embedded != null && embedded.has(ApiFieldConstants.VENUES)) {
            JsonNode venuesNode = embedded.get(ApiFieldConstants.VENUES);
            if (venuesNode.isArray() && venuesNode.size() > 0) {
                JsonNode venueNode = venuesNode.get(0);
                this.venue = new Venue();
                this.venue.setId(venueNode.get(ApiFieldConstants.ID).asText());
                this.venue.setName(venueNode.get(ApiFieldConstants.NAME).asText());
                if (venueNode.has(ApiFieldConstants.CITY)) {
                    this.venue.setCity(venueNode.get(ApiFieldConstants.CITY).get(ApiFieldConstants.NAME).asText());
                }
                if (venueNode.has(ApiFieldConstants.STATE)) {
                    this.venue.setState(venueNode.get(ApiFieldConstants.STATE).get(ApiFieldConstants.STATE_CODE).asText());
                }
                if (venueNode.has(ApiFieldConstants.COUNTRY)) {
                    this.venue.setCountry(venueNode.get(ApiFieldConstants.COUNTRY).get(ApiFieldConstants.NAME).asText());
                }
            }
        }
    }

    public Event() {}

    public Event(String id, String name, String type, LocalDate date,
                 String url, Classification classification, Venue venue) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.date = date;
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
