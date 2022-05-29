package com.backbase.movieawards.api;

import com.backbase.movieawards.model.MovieDetailsResponse;
import com.backbase.movieawards.service.MovieAwardsResponseAssembler;
import com.backbase.movieawards.service.MovieAwardsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/movie-awards")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MovieAwardsController {

  private final MovieAwardsService movieAwardsService;
  private final MovieAwardsResponseAssembler movieAwardsResponseAssembler;

  @GetMapping(path = "/best-picture")
  public ResponseEntity<MovieDetailsResponse> getBestPicture(@RequestParam final String name, @RequestParam final String year) {
    final var movieDetails = movieAwardsService.getMovieDetails(name, year);
    final var movieDetailsResponse = movieAwardsResponseAssembler.assemble(movieDetails);
    return ResponseEntity.ok(movieDetailsResponse);
  }
}
