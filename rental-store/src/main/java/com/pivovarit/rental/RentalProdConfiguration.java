package com.pivovarit.rental;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@Profile("prod")
class RentalProdConfiguration {

    @Bean
    public MovieRepository jdbcMovieRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcMovieRepository(jdbcTemplate);
    }

    @Bean
    public RentalHistoryRepository jdbcRentalHistoryRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcRentalHistoryRepository(jdbcTemplate);
    }
}
