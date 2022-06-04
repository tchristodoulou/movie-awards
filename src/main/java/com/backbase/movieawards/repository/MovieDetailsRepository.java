package com.backbase.movieawards.repository;

import com.backbase.movieawards.repository.entity.MovieDetails;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieDetailsRepository extends JpaRepository<MovieDetails, UUID> {

  Optional<MovieDetails> findByNomineeAndYearAndCategory(final String nominee, final String year, final String category);
}
