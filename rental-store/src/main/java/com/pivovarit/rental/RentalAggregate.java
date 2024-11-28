package com.pivovarit.rental;

import java.util.List;

sealed interface RentalAggregate permits Movie, UserRentals {
    void apply(MovieRentalEvent event);

    default void apply(List<MovieRentalEvent> events) {
        events.forEach(this::apply);
    }
}
