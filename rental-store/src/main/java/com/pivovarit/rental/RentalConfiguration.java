package com.pivovarit.rental;

import com.pivovarit.account.AccountFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
class RentalConfiguration {

    @Bean
    public MovieRentalService movieRentalService(RentalHistoryRepository rentalHistoryRepository, MovieRepository movieRepository) {
        return new MovieRentalService(rentalHistoryRepository, movieRepository);
    }

    @Bean
    public RentalFacade rentalFacade(MovieRepository movieRepository, MovieDescriptionsRepository movieDescriptions, MovieRentalService movieRentalService, AccountFacade accounts) {
        return new RentalFacade(movieRepository, movieDescriptions, movieRentalService, accounts);
    }

    @Bean
    public MovieDescriptionsRepository httpMovieDescriptionsRepository() {
        return new StubMovieDescriptionsService();
    }
}
