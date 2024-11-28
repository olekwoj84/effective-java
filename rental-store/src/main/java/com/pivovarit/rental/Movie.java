package com.pivovarit.rental;

import com.pivovarit.rental.api.MovieId;

import java.util.Optional;

final class Movie implements RentalAggregate {

    private final MovieId id;
    private final String title;
    private final MovieType type;

    public Movie(MovieId id, String title, MovieType type) {
        this.id = id;
        this.title = title;
        this.type = type;
    }

    private String renter;

    public boolean canBeRented() {
        return renter == null;
    }

    public boolean canBeReturned(String login) {
        return renter != null && renter.equals(login);
    }

    @Override
    public void apply(MovieRentalEvent event) {
        switch (event.type()) {
            case MOVIE_RENTED -> renter = event.login();
            case MOVIE_RETURNED -> renter = null;
        }
    }

    public Optional<String> getRenter() {
        return Optional.ofNullable(renter);
    }

    public MovieId getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public MovieType getType() {
        return type;
    }
}
