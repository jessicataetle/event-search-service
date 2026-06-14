package com.ticketmaster.eventdiscovery.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Classification {
    private Segment segment;
    private Genre genre;
    private SubGenre subGenre;

    public Classification() {}

    public Classification(Segment segment, Genre genre, SubGenre subGenre) {
        this.segment = segment;
        this.genre = genre;
        this.subGenre = subGenre;
    }

    public Segment getSegment() {
        return segment;
    }

    public void setSegment(Segment segment) {
        this.segment = segment;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public SubGenre getSubGenre() {
        return subGenre;
    }

    public void setSubGenre(SubGenre subGenre) {
        this.subGenre = subGenre;
    }
}
