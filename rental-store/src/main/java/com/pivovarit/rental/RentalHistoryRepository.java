package com.pivovarit.rental;

import com.pivovarit.rental.api.MovieId;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;

interface RentalHistoryRepository {
    void saveRentEvent(MovieId id, String login, long eventId);

    void saveReturnEvent(MovieId id, String login, long eventId);

    long lastEventId();

    List<MovieRentalEvent> findAll();

    List<MovieRentalEvent> findUserRentals(String user);

    List<MovieRentalEvent> findMovieRentals(MovieId movieId);

    List<MovieRentalEvent> getUnprocessed();

    void markProcessed(MovieRentalEvent event);

    void saveEvent(MovieRentalEventType eventType, String login, MovieId id, long eventId);
}
