package com.ticketmaster.eventdiscovery.controller;

import com.ticketmaster.eventdiscovery.model.Event;
import com.ticketmaster.eventdiscovery.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/{eventId}")
    public Event getEvent(@PathVariable String eventId) {
        return eventService.getEventById(eventId);
    }

    @GetMapping
    public List<Event> searchEvents(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String subGenre,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        return eventService.searchEvents(keyword, location, genre, subGenre, page, size);
    }

    @GetMapping("/{eventId}/similar")
    public List<Event> getSimilarEvents(@PathVariable String eventId) {
        return eventService.findSimilarEvents(eventId);
    }
}
