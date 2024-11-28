package com.pivovarit.rental;

import com.pivovarit.rental.api.MovieId;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryMovieRepository implements MovieRepository {

    private final ConcurrentHashMap<MovieId, Movie> movies = new ConcurrentHashMap<>();

    @Override
    public MovieId save(Movie movie) {
        movies.put(movie.getId(), movie);
        return movie.getId();
    }

    @Override
    public Collection<Movie> findAll() {
        return movies.values();
    }

    @Override
    public Collection<Movie> findByType(String type) {
        return movies.values()
          .stream()
          .filter(m -> m.getType().toString().equals(type))
          .toList();
    }

    @Override
    public Optional<Movie> findById(MovieId id) {
        return Optional.ofNullable(movies.get(id));
    }
}
