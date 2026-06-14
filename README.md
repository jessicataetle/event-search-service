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
git clone https://github.com/jessicataetle/event-search-service.git
cd event-search-service
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
├── exception/
│   ├── EventNotFoundException.java      # Custom 404 exception
│   └── GlobalExceptionHandler.java      # Centralized error handling
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

## Error Handling

The application includes centralized error handling via `GlobalExceptionHandler`:

- **404 Not Found** — Returned when an event is not found in the Ticketmaster API
- **500 Internal Server Error** — Returned for unexpected server-side errors
- All error responses include timestamp, status code, error type, and message

**Example Error Response:**
```json
{
  "timestamp": "2024-01-15T10:30:45.123456",
  "status": 404,
  "error": "Not Found",
  "message": "Event not found with ID: invalid-id"
}
```

## Configuration

Configuration is managed via `application.properties`:

```properties
spring.application.name=event-discovery-service
server.port=8080
ticketmaster.api.key=YOUR_API_KEY_HERE
ticketmaster.api.baseurl=https://app.ticketmaster.com/discovery/v2
```

The API key is required for all requests to the Ticketmaster Discovery API.

## Technologies

- **Spring Boot 3.1.5** — Application framework
- **Spring Web** — REST API support
- **RestTemplate** — HTTP client for external API calls
- **Jackson** — JSON serialization/deserialization
- **Maven** — Build and dependency management

## License

This project is provided as-is for educational and development purposes.