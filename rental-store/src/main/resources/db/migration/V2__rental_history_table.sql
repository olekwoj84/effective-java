CREATE TABLE RENTAL_HISTORY
(
    id        SERIAL PRIMARY KEY      NOT NULL,
    timestamp timestamp DEFAULT now() NOT NULL,
    type      TEXT                    NOT NULL,
    login     TEXT                    NOT NULL,
    movie_id  BIGINT                  NOT NULL
)
