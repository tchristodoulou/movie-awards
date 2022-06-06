CREATE TABLE IF NOT EXISTS movie_reviews (
                                          `id` VARCHAR(36) NOT NULL PRIMARY KEY,
                                          `movie_year` VARCHAR(4),
                                          `title` VARCHAR(255),
                                          `review_score` INT
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;