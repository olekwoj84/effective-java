package com.pivovarit.rental;

import com.pivovarit.rental.api.MovieId;

import java.util.Collection;
import java.util.Optional;

interface MovieRepository {
    MovieId save(Movie movie);

    Collection<Movie> findAll();

    Collection<Movie> findByType(String type);

    Optional<Movie> findById(MovieId id);
}
