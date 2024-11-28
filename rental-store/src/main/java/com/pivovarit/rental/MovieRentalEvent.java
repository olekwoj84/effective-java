package com.pivovarit.rental;

import com.pivovarit.rental.api.MovieId;

record MovieRentalEvent(long id, String timestamp, MovieRentalEventType type, MovieId movieId, String login) {
}
