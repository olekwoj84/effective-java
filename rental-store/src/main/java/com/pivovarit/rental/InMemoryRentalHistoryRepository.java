package com.pivovarit.rental;

import com.pivovarit.rental.api.MovieId;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class InMemoryRentalHistoryRepository implements RentalHistoryRepository {

    private final List<MovieRentalEvent> events = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void saveRentEvent(MovieId id, String login, long eventId) {
        events.add(new MovieRentalEvent(eventId, Instant.now().toString(), MovieRentalEventType.MOVIE_RENTED, id, login));
    }

    @Override
    public void saveReturnEvent(MovieId id, String login, long eventId) {
        events.add(new MovieRentalEvent(eventId, Instant.now().toString(), MovieRentalEventType.MOVIE_RETURNED, id, login));
    }

    @Override
    public long lastEventId() {
        return events.isEmpty() ? -1 : events.size() - 1;
    }

    @Override
    public List<MovieRentalEvent> findAll() {
        return events;
    }

    @Override
    public List<MovieRentalEvent> findUserRentals(String user) {
        return events.stream().filter(e -> e.login().equals(user)).toList();
    }

    @Override
    public List<MovieRentalEvent> findMovieRentals(MovieId movieId) {
        return events.stream().filter(e -> e.movieId().equals(movieId)).toList();
    }

    @Override
    public List<MovieRentalEvent> getUnprocessed() {
        return List.of();
    }

    @Override
    public void markProcessed(MovieRentalEvent event) {
    }

    @Override
    public void saveEvent(MovieRentalEventType eventType, String login, MovieId id, long eventId) {
        events.add(new MovieRentalEvent(eventId, Instant.now().toString(), eventType, id, login));
    }
}
