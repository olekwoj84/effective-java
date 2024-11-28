package com.pivovarit.rental;

import com.pivovarit.account.AccountFacade;
import com.pivovarit.rental.api.MovieAddRequest;
import com.pivovarit.rental.api.MovieDto;
import com.pivovarit.rental.api.MovieId;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MovieServiceTest {

    private static final int SPIDERMAN_ID = 1;
    private static final String SPIDERMAN_DESCRIPTION = "After being bitten by a genetically-modified spider, a shy teenager gains spider-like abilities that he uses to fight injustice as a masked superhero";

    @RepeatedTest(10_000)
    void shouldSaveMovie() {
        var service = inMemoryInstance();
        MovieAddRequest movie = new MovieAddRequest(SPIDERMAN_ID, "Spiderman", "NEW");

        service.addMovie(movie);

        MovieDto result = service.findMovieById(new MovieId(movie.id())).orElseThrow();

        assertThat(result.id()).isEqualTo(movie.id());
        assertThat(result.title()).isEqualTo(movie.title());
        assertThat(result.type()).isEqualTo(movie.type());
        assertThat(result.description()).isEqualTo(SPIDERMAN_DESCRIPTION);
    }

    private static RentalFacade inMemoryInstance() {
        InMemoryMovieRepository movieRepository = new InMemoryMovieRepository();
        return new RentalFacade(movieRepository, new StubMovieDescriptionsService(), new MovieRentalService(new InMemoryRentalHistoryRepository(), movieRepository), new AccountFacade());
    }
}
