package com.backbase.movieawards.repository;

import com.backbase.movieawards.repository.entity.MovieReview;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieReviewRepository extends JpaRepository<MovieReview, UUID> {

}
