package com.pivovarit.rental;

import com.pivovarit.rental.api.MovieId;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Collection;
import java.util.Optional;

class JdbcMovieRepository implements MovieRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcMovieRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public MovieId save(Movie movie) {
        jdbcTemplate.update("INSERT INTO MOVIES(id, title, type) VALUES(?, ?, ?)", movie.getId()
          .id(), movie.getTitle(), movie.getType().toString());

        return movie.getId();
    }

    @Override
    public Collection<Movie> findAll() {
        return jdbcTemplate.query("SELECT * FROM MOVIES", getMovieRowMapper());
    }

    @Override
    public Collection<Movie> findByType(String type) {
        return jdbcTemplate.query("SELECT * FROM MOVIES m WHERE m.type = ?", getMovieRowMapper(), type);
    }

    @Override
    public Optional<Movie> findById(MovieId id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM MOVIES m WHERE m.id = ?", getMovieRowMapper(), id.id()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private static RowMapper<Movie> getMovieRowMapper() {
        return (rs, rowNum) -> new Movie(new MovieId(rs.getLong("id")), rs.getString("title"), MovieType.valueOf(rs.getString("type")));
    }
}
