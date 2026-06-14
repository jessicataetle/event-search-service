# Event Discovery Service

A Spring Boot application for searching and discovering events using the Ticketmaster Discovery API.

## Features

- **Event Search** — Search events by keyword, location, genre, and subgenre with pagination
- **Event Details** — Fetch detailed information about a specific event
- **Similar Events** — Find similar events based on genre, subgenre, location, and date range
- **RESTful API** — Clean REST endpoints for event discovery
- **External API Integration** — Seamless integration with Ticketmaster Discovery API

## Prerequisites

- Java 17 or later
- Maven 3.6+
- Ticketmaster API key (get one at https://developer.ticketmaster.com)

## Setup

1. Clone the repository:
```bash
git clone <repository-url>
cd seat-reservation-service
```

2. Configure your API key in `src/main/resources/application.properties`:
```properties
ticketmaster.api.key=YOUR_API_KEY_HERE
```

3. Build the project:
```bash
mvn clean install
```

## Running the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

### Get Event Details
```
GET /events/{eventId}
```
Retrieves detailed information about a specific event.

**Example:**
```bash
curl http://localhost:8080/events/G5iqslay1V686
```

### Search Events
```
GET /events?keyword=concert&location=New+York&genre=KZFzniwnSyZfZ7v7nJ&subGenre=KZazBEonSMnZaz7vFJ&page=0&size=20
```
Searches for events with optional filters and pagination.

**Query Parameters:**
- `keyword` (optional) — Event name or keyword
- `location` (optional) — City name
- `genre` (optional) — Genre ID
- `subGenre` (optional) — SubGenre ID
- `page` (optional, default: 0) — Page number for pagination
- `size` (optional, default: 20) — Number of results per page

**Example:**
```bash
curl "http://localhost:8080/events?keyword=concert&location=New+York&page=0&size=10"
```

### Find Similar Events
```
GET /events/{eventId}/similar
```
Finds events similar to the specified event based on genre, subgenre, location, and date range (±30 days).

**Example:**
```bash
curl http://localhost:8080/events/G5iqslay1V686/similar
```

## Project Structure

```
src/main/java/com/ticketmaster/eventdiscovery/
├── EventDiscoveryApplication.java      # Main Spring Boot application
├── config/
│   └── RestTemplateConfig.java         # RestTemplate configuration
├── controller/
│   └── EventController.java            # REST endpoints
├── service/
│   └── EventService.java               # Business logic
├── repository/
│   └── EventRepository.java            # Ticketmaster API client
└── model/
    ├── Event.java                      # Event entity
    ├── Classification.java             # Event classification
    ├── Genre.java                      # Genre classification
    ├── SubGenre.java                   # SubGenre classification
    ├── Segment.java                    # Event segment
    └── Venue.java                      # Venue information
```

## Architecture

The application follows a layered architecture pattern:

1. **Controller Layer** — Handles HTTP requests and responses
2. **Service Layer** — Contains business logic and event processing
3. **Repository Layer** — Manages API communication with Ticketmaster Discovery API
4. **Model Layer** — Data classes representing API responses

## Configuration

Configuration is managed via `application.properties`:

```properties
spring.application.name=event-discovery-service
server.port=8080
ticketmaster.api.key=YOUR_API_KEY_HERE
ticketmaster.api.baseurl=https://app.ticketmaster.com/discovery/v2
```

## Technologies

- **Spring Boot 3.1.5** — Application framework
- **Spring Web** — REST API support
- **RestTemplate** — HTTP client for external API calls
- **Jackson** — JSON serialization/deserialization
- **Maven** — Build and dependency management

## License

This project is provided as-is for educational and development purposes.