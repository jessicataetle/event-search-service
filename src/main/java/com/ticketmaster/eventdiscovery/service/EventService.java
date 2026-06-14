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

        String genre = sourceEvent.getClassification() != null &&
                sourceEvent.getClassification().getGenre() != null
                ? sourceEvent.getClassification().getGenre().getId()
                : null;

        String subGenre = sourceEvent.getClassification() != null &&
                sourceEvent.getClassification().getSubGenre() != null
                ? sourceEvent.getClassification().getSubGenre().getId()
                : null;

        String location = sourceEvent.getVenue() != null
                ? sourceEvent.getVenue().getCity()
                : null;

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
        }

        return similarEvents;
    }
}
