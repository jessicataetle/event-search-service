package com.ticketmaster.eventdiscovery.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
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

    @JsonProperty("dates")
    private void setDates(JsonNode dates) {
        if (dates != null && dates.has("start")) {
            JsonNode start = dates.get("start");
            if (start.has("localDate")) {
                this.date = LocalDate.parse(start.get("localDate").asText());
            }
            if (start.has("localTime")) {
                this.time = LocalTime.parse(start.get("localTime").asText());
            }
        }
    }

    @JsonProperty("classifications")
    private void setClassifications(JsonNode[] classifications) {
        if (classifications != null && classifications.length > 0) {
            this.classification = new Classification();
            JsonNode firstClassification = classifications[0];
            if (firstClassification.has("genre")) {
                Genre genre = new Genre();
                genre.setId(firstClassification.get("genre").get("id").asText());
                genre.setName(firstClassification.get("genre").get("name").asText());
                this.classification.setGenre(genre);
            }
            if (firstClassification.has("subGenre")) {
                SubGenre subGenre = new SubGenre();
                subGenre.setId(firstClassification.get("subGenre").get("id").asText());
                subGenre.setName(firstClassification.get("subGenre").get("name").asText());
                this.classification.setSubGenre(subGenre);
            }
        }
    }

    @JsonProperty("_embedded")
    private void setEmbedded(JsonNode embedded) {
        if (embedded != null && embedded.has("venues")) {
            JsonNode venuesNode = embedded.get("venues");
            if (venuesNode.isArray() && venuesNode.size() > 0) {
                JsonNode venueNode = venuesNode.get(0);
                this.venue = new Venue();
                this.venue.setId(venueNode.get("id").asText());
                this.venue.setName(venueNode.get("name").asText());
                if (venueNode.has("city")) {
                    this.venue.setCity(venueNode.get("city").get("name").asText());
                }
                if (venueNode.has("state")) {
                    this.venue.setState(venueNode.get("state").get("stateCode").asText());
                }
                if (venueNode.has("country")) {
                    this.venue.setCountry(venueNode.get("country").get("name").asText());
                }
            }
        }
    }

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
