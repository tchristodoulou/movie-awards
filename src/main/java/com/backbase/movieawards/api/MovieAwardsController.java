package com.backbase.movieawards.api;

import com.backbase.movieawards.model.MovieDetailsResponse;
import com.backbase.movieawards.service.MovieAwardsResponseAssembler;
import com.backbase.movieawards.service.MovieAwardsService;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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

  @GetMapping(path = "/best-picture")
  public ResponseEntity<MovieDetailsResponse> getBestPicture(@RequestParam @NotBlank final String name,
      @RequestParam @NotBlank final String year) {
    log.info("Received request for title [{}] and year [{}]", name, year);
    final var movieDetails = movieAwardsService.getMovieDetails(name, year);
    final var movieDetailsResponse = movieAwardsResponseAssembler.assemble(movieDetails);
    return ResponseEntity.ok(movieDetailsResponse);
  }
}
