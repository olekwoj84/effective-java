package com.pivovarit.rental;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
class RentalDevConfiguration {

    @Bean
    public MovieRepository jdbcMovieRepository() {
        return new InMemoryMovieRepository();
    }

    @Bean
    public RentalHistoryRepository jdbcRentalHistoryRepository() {
        return new InMemoryRentalHistoryRepository();
    }
}
