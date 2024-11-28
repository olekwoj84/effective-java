package com.pivovarit.rental.web;

import com.pivovarit.rental.RentalFacade;
import com.pivovarit.rental.api.MovieAddRequest;
import com.pivovarit.rental.api.MovieDto;
import com.pivovarit.rental.api.MovieId;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Optional;

@RestController
public class RentalController {

    // curl -X POST -H "Content-Type: application/json" -d '{"id": 1, "title": "The Matrix", "type": "NEW"}' http://localhost:8081/movies
    // curl -X POST -H "Content-Type: application/json" -d '{"id": 2, "title": "The Godfather", "type": "REGULAR"}' http://localhost:8081/movies
    // curl -X POST -H "Content-Type: application/json" -d '{"id": 3, "title": "The Lord of the Rings", "type": "OLD"}' http://localhost:8081/movies
    // curl -X POST -H "Content-Type: application/json" -d '{"id": 4, "title": "Pulp Fiction", "type": "REGULAR"}' http://localhost:8081/movies
    // curl -X POST -H "Content-Type: application/json" -d '{"id": 5, "title": "The Good, the Bad and the Ugly", "type": "OLD"}' http://localhost:8081/movies
    // curl -X POST -H "Content-Type: application/json" -d '{"id": 6, "title": "The Dark Knight", "type": "NEW"}' http://localhost:8081/movies
    private final RentalFacade rentalFacade;

    RentalController(RentalFacade rentalFacade) {
        this.rentalFacade = rentalFacade;
    }

    @GetMapping("/movies")
    public Collection<MovieDto> getMovies(@RequestParam(required = false) String type) {
        return type == null ? rentalFacade.findAllMovies() : rentalFacade.findMovieByType(type);
    }

    @GetMapping("/movies/{id}")
    public Optional<MovieDto> getMoviesById(@PathVariable int id) {
        return rentalFacade.findMovieById(new MovieId(id));
    }

    @PostMapping("/movies")
    public void addMovie(@RequestBody MovieAddRequest request) {
        rentalFacade.addMovie(request);
    }

    @PostMapping("/movies/{id}/rent")
    public void rentMovie(@RequestBody Renter renter, @PathVariable long id) {
        rentalFacade.rentMovie(renter.login(), new MovieId(id));
    }

    @PostMapping("/movies/{id}/return")
    public void returnMovie(@RequestBody Renter renter, @PathVariable long id) {
        rentalFacade.returnMovie(renter.login(), new MovieId(id));
    }

    private record Renter(String login) {
    }

    // curl --header "Content-Type: application/json" --request POST --data '{ "id":14, "title":"spiderman", "type":"NEW"}' http://localhost:8081/movies
}
