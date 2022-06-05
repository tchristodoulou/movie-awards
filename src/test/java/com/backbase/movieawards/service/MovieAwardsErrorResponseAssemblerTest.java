package com.backbase.movieawards.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MovieAwardsErrorResponseAssemblerTest {

  private static final String FIELD = "field";
  private static final String ERROR = "error";

  private MovieAwardsErrorResponseAssembler subject;

  @BeforeEach
  void onBeforeEach() {
    subject = new MovieAwardsErrorResponseAssembler();
  }

  @Test
  void assemble_WithFieldAndError_ReturnsMovieAwardsErrorResponse() {
    final var response = subject.assemble(FIELD, ERROR);
    assertNotNull(response);
    assertEquals(FIELD, response.getField());
    assertEquals(ERROR, response.getError());
  }
}