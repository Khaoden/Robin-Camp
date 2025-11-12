CREATE TABLE movies (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(500) NOT NULL UNIQUE,
    release_date DATE NOT NULL,
    genre VARCHAR(100) NOT NULL,
    distributor VARCHAR(200),
    budget BIGINT,
    mpa_rating VARCHAR(10),
    revenue_worldwide BIGINT,
    revenue_opening_weekend_usa BIGINT,
    box_office_currency VARCHAR(10),
    box_office_source VARCHAR(200),
    box_office_last_updated TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_movie_title ON movies(title);
CREATE INDEX idx_movie_release_date ON movies(release_date);
CREATE INDEX idx_movie_genre ON movies(genre);
CREATE INDEX idx_movie_distributor ON movies(LOWER(distributor));
CREATE INDEX idx_movie_year ON movies(EXTRACT(YEAR FROM release_date));

CREATE TABLE ratings (
    id BIGSERIAL PRIMARY KEY,
    movie_id BIGINT NOT NULL REFERENCES movies(id) ON DELETE CASCADE,
    rater_id VARCHAR(200) NOT NULL,
    rating DECIMAL(2,1) NOT NULL CHECK (rating >= 0.5 AND rating <= 5.0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(movie_id, rater_id)
);

CREATE INDEX idx_rating_movie ON ratings(movie_id);
CREATE INDEX idx_rating_rater ON ratings(rater_id);

