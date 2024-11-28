package com.pivovarit.rental.api.exception;

import com.pivovarit.rental.api.MovieId;

public class MovieNotExistsException extends RuntimeException {

    public MovieNotExistsException(MovieId movieId) {
        super("movie with id: " + movieId.id() + " does not exist");
    }
}
