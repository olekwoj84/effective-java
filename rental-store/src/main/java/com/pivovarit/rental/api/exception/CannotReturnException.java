package com.pivovarit.rental.api.exception;

import com.pivovarit.rental.api.MovieId;

public class CannotReturnException extends RuntimeException {

    public CannotReturnException(MovieId movieId) {
        super("can't return movie with id: " + movieId);
    }
}
