package com.for_comprehension.function.E2.original;

import java.util.Optional;

// https://martinfowler.com/bliki/StranglerFigApplication.html
record ModernAPI(LegacyAPI legacyAPI) {

    private Optional<LegacyAPI.Person> findPerson(int id) {
        return Optional.ofNullable(legacyAPI.findPerson(id));
    }

    private Optional<String> findAddress(LegacyAPI.Person person) {
        return Optional.ofNullable(legacyAPI.findAddress(person));
    }

    private Optional<String> findAddressById(int id) {
        return Optional.ofNullable(legacyAPI.findAddressById(id));
    }
}
