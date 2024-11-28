package com.pivovarit.rental;

import com.pivovarit.rental.api.MovieId;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

record JdbcRentalHistoryRepository(JdbcTemplate jdbcTemplate) implements RentalHistoryRepository {

    @Override
    public void saveRentEvent(MovieId id, String login, long eventId) {
        saveEvent(MovieRentalEventType.MOVIE_RENTED, login, id, eventId);
    }

    @Override
    public void saveReturnEvent(MovieId id, String login, long eventId) {
        saveEvent(MovieRentalEventType.MOVIE_RETURNED, login, id, eventId);
    }

    @Override
    public long lastEventId() {
        return jdbcTemplate.queryForObject("SELECT COALESCE(max(id), 0) FROM RENTAL_HISTORY", Long.class);
    }

    @Override
    public List<MovieRentalEvent> findAll() {
        return jdbcTemplate.query("SELECT * FROM rental_history ORDER BY id", toMovieRentalEvent());
    }

    @Override
    public List<MovieRentalEvent> findUserRentals(String user) {
        return jdbcTemplate.query("SELECT * FROM rental_history WHERE login = ? ORDER BY id", toMovieRentalEvent(), user);
    }

    @Override
    public List<MovieRentalEvent> findMovieRentals(MovieId movieId) {
        return jdbcTemplate.query("SELECT * FROM rental_history WHERE movie_id = ? ORDER BY id", toMovieRentalEvent(), movieId.id());
    }

    @Override
    public List<MovieRentalEvent> getUnprocessed() {
        return jdbcTemplate.query("SELECT * FROM rental_history WHERE processed = false ORDER BY id", toMovieRentalEvent());
    }

    @Override
    public void markProcessed(MovieRentalEvent event) {
        jdbcTemplate.update("UPDATE rental_history SET processed = true WHERE id = ?", event.id());
    }

    @Override
    public void saveEvent(MovieRentalEventType eventType, String login, MovieId id, long eventId) {
        try {
            jdbcTemplate.update("INSERT INTO rental_history(id, type, login, movie_id) VALUES(?, ?,?,?)", eventId, eventType.toString(), login, id.id());
        } catch (DuplicateKeyException e) {
            throw new ConcurrentLogWriteException();
        }
    }

    private static RowMapper<MovieRentalEvent> toMovieRentalEvent() {
        return (rs, __) -> {
            var id = rs.getLong("id");
            var timestamp = rs.getString("timestamp");
            var type = MovieRentalEventType.valueOf(rs.getString("type"));
            var movieId = new MovieId(rs.getLong("movie_id"));
            var login = rs.getString("login");
            return new MovieRentalEvent(id, timestamp, type, movieId, login);
        };
    }
}
