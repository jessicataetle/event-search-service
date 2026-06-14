package com.ticketmaster.eventdiscovery.repository;

import com.ticketmaster.eventdiscovery.model.Event;
import com.ticketmaster.eventdiscovery.exception.EventNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EventRepository {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${ticketmaster.api.baseurl}")
    private String baseUrl;

    @Value("${ticketmaster.api.key}")
    private String apiKey;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Event findEventById(String eventId) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/events/" + eventId)
                .queryParam("apikey", apiKey)
                .toUriString();

        try {
            String response = restTemplate.getForObject(url, String.class);
            return objectMapper.readValue(response, Event.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new EventNotFoundException("Event not found with ID: " + eventId);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching event by ID: " + eventId, e);
        }
    }

    public List<Event> searchEvents(String keyword, String location, String genre,
                                     String subGenre, int page, int size) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl + "/events")
                .queryParam("apikey", apiKey);

        if (keyword != null && !keyword.isEmpty()) {
            builder.queryParam("keyword", keyword);
        }
        if (location != null && !location.isEmpty()) {
            builder.queryParam("city", location);
        }
        if (genre != null && !genre.isEmpty()) {
            builder.queryParam("genreId", genre);
        }
        if (subGenre != null && !subGenre.isEmpty()) {
            builder.queryParam("subGenreId", subGenre);
        }

        builder.queryParam("page", page);
        builder.queryParam("size", size);

        String url = builder.toUriString();

        try {
            String response = restTemplate.getForObject(url, String.class);
            return parseEventsFromResponse(response);
        } catch (Exception e) {
            throw new RuntimeException("Error searching events", e);
        }
    }

    private List<Event> parseEventsFromResponse(String response) throws Exception {
        List<Event> events = new ArrayList<>();
        JsonNode root = objectMapper.readTree(response);

        if (root.has("_embedded") && root.get("_embedded").has("events")) {
            JsonNode eventsNode = root.get("_embedded").get("events");
            for (JsonNode eventNode : eventsNode) {
                Event event = objectMapper.treeToValue(eventNode, Event.class);
                events.add(event);
            }
        }

        return events;
    }
}
