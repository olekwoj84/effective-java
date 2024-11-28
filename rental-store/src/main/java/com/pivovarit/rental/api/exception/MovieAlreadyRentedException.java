package com.pivovarit.rental.api.exception;

import com.pivovarit.rental.api.MovieId;

public class MovieAlreadyRentedException extends RuntimeException {

    public MovieAlreadyRentedException(MovieId movieId) {
        super("movie with id: " + movieId.id() + " is already rented");
    }
}
