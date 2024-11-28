package com.pivovarit.rental;

import com.pivovarit.account.AccountFacade;
import com.pivovarit.rental.api.MovieAddRequest;
import com.pivovarit.rental.api.MovieDto;
import com.pivovarit.rental.api.MovieId;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;

public class RentalFacade {

    private final MovieRepository movieRepository;
    private final MovieDescriptionsRepository movieDescriptionsService;
    private final MovieRentalService movieRentalService;
    private final AccountFacade account;

    public RentalFacade(MovieRepository movieRepository, MovieDescriptionsRepository movieDescriptionsService, MovieRentalService movieRentalService, AccountFacade account) {
        this.movieRepository = movieRepository;
        this.movieDescriptionsService = movieDescriptionsService;
        this.movieRentalService = movieRentalService;
        this.account = account;
    }

    public void rentMovie(String login, MovieId id) {
        movieRentalService.rentMovie(login, id);
    }

    public void returnMovie(String login, MovieId id) {
        movieRentalService.returnMovie(login, id);
    }

    public void addMovie(MovieAddRequest request) {
        movieRepository.save(MovieConverters.from(request));
    }

    public Collection<MovieDto> findAllMovies() {
        return movieRepository.findAll().stream().map(toMovieWithDescription()).toList();
    }

    public Collection<MovieDto> findMovieByType(String type) {
        return movieRepository.findByType(type).stream().map(toMovieWithDescription()).toList();
    }

    public Optional<MovieDto> findMovieById(MovieId id) {
        return movieRepository.findById(id).map(toMovieWithDescription());
    }

    private Function<Movie, MovieDto> toMovieWithDescription() {
        return m -> MovieConverters.from(m, movieDescriptionsService.findByMovieId(m.getId().id())
          .orElse(new MovieDescription("")).description());
    }
}
