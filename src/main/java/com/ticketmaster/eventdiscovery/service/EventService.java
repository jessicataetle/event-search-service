package com.ticketmaster.eventdiscovery.service;

import com.ticketmaster.eventdiscovery.model.Event;
import com.ticketmaster.eventdiscovery.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Event getEventById(String eventId) {
        return eventRepository.findEventById(eventId);
    }

    public List<Event> searchEvents(String keyword, String location, String genre,
                                     String subGenre, int page, int size) {
        return eventRepository.searchEvents(keyword, location, genre, subGenre, page, size);
    }

    public List<Event> findSimilarEvents(String eventId) {
        Event sourceEvent = eventRepository.findEventById(eventId);

        String genre = null;
        String subGenre = null;
        String location = null;

        if (sourceEvent.getClassification() != null) {
            if (sourceEvent.getClassification().getGenre() != null) {
                genre = sourceEvent.getClassification().getGenre().getId();
            }
            if (sourceEvent.getClassification().getSubGenre() != null) {
                subGenre = sourceEvent.getClassification().getSubGenre().getId();
            }
        }

        if (sourceEvent.getVenue() != null && sourceEvent.getVenue().getCity() != null) {
            location = sourceEvent.getVenue().getCity();
        }

        if (genre == null && location == null) {
            return new java.util.ArrayList<>();
        }

        List<Event> similarEvents = eventRepository.searchEvents(null, location, genre, subGenre, 0, 20);

        LocalDate eventDate = sourceEvent.getDate();
        if (eventDate != null) {
            LocalDate startDate = eventDate.minusDays(30);
            LocalDate endDate = eventDate.plusDays(30);

            similarEvents = similarEvents.stream()
                    .filter(event -> event.getDate() != null &&
                            !event.getDate().isBefore(startDate) &&
                            !event.getDate().isAfter(endDate) &&
                            !event.getId().equals(eventId))
                    .collect(Collectors.toList());
        } else {
            similarEvents = similarEvents.stream()
                    .filter(event -> !event.getId().equals(eventId))
                    .collect(Collectors.toList());
        }

        return similarEvents;
    }
}
