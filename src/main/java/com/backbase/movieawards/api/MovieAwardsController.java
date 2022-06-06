package com.backbase.movieawards.api;

import com.backbase.movieawards.model.MovieReviewRequest;
import com.backbase.movieawards.model.MovieDetailsResponse;
import com.backbase.movieawards.service.MovieAwardsResponseAssembler;
import com.backbase.movieawards.service.MovieAwardsService;
import com.backbase.movieawards.service.MovieReviewResponseAssembler;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping(path = "/movie-awards")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class MovieAwardsController {

  private final MovieAwardsService movieAwardsService;
  private final MovieAwardsResponseAssembler movieAwardsResponseAssembler;
  private final MovieReviewResponseAssembler movieReviewResponseAssembler;

  @GetMapping(path = "/best-picture")
  public ResponseEntity<MovieDetailsResponse> getBestPicture(@RequestParam @NotBlank final String name,
      @RequestParam @NotBlank final String year) {
    log.info("Received best picture request for title [{}] and year [{}]", name, year);
    final var movieDetails = movieAwardsService.getMovieDetails(name, year);
    final var movieDetailsResponse = movieAwardsResponseAssembler.assemble(movieDetails);
    return ResponseEntity.ok(movieDetailsResponse);
  }

  @PostMapping(path = "/review")
  public ResponseEntity<MovieDetailsResponse> saveReview(@Valid @RequestBody final MovieReviewRequest movieDetailsRequest) {
    log.info("Received review request for title [{}] and year [{}]", movieDetailsRequest.getTitle(), movieDetailsRequest.getMovieYear());
    final var movieReview = movieAwardsService.saveReview(movieDetailsRequest);
    final var movieDetailsResponse = movieReviewResponseAssembler.assemble(movieReview);
    return new ResponseEntity(movieDetailsResponse, HttpStatus.CREATED);
  }
}
