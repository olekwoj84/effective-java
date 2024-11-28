package com.pivovarit.rental;

import java.util.Optional;

interface MovieDescriptionsRepository {
    Optional<MovieDescription> findByMovieId(long movieId);
}
