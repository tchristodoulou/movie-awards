package com.backbase.movieawards.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.flywaydb.core.internal.util.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MovieReviewRequestTest {

  private static final String TITLE = "test";
  private static final String MOVIE_YEAR = "2022";
  private static final Integer REVIEW_SCORE = 75;

  private ValidatorFactory validatorFactory;
  private Validator validator;

  @BeforeEach
  void setUp() {
    validatorFactory = Validation.buildDefaultValidatorFactory();
    validator = validatorFactory.getValidator();
  }

  @Test
  void movieReviewRequest_ReturnsSuccessful_MandatoryFieldsPopulated() {
    final var movieReviewRequest =
        MovieReviewRequest.builder()
            .title(TITLE)
            .movieYear(MOVIE_YEAR)
            .reviewScore(REVIEW_SCORE)
            .build();

    final var violations = validator.validate(movieReviewRequest);
    assertTrue(violations.isEmpty());
  }

  @Test
  void movieReviewRequest_WithTitleSetToNull_ReturnsBlankError() {
    final var movieReviewRequest =
        MovieReviewRequest.builder()
            .title(null)
            .movieYear(MOVIE_YEAR)
            .reviewScore(REVIEW_SCORE)
            .build();

    final var violations = validator.validate(movieReviewRequest);
    assertEquals(1, violations.size());

    final var violation = violations.iterator().next();
    assertEquals("title", violation.getPropertyPath().toString());
    assertEquals("must not be blank", violation.getMessage());
  }

  @Test
  void movieReviewRequest_WithTitleSetToBlank_ReturnsBlankError() {
    final var movieReviewRequest =
        MovieReviewRequest.builder()
            .title("")
            .movieYear(MOVIE_YEAR)
            .reviewScore(REVIEW_SCORE)
            .build();

    final var violations = validator.validate(movieReviewRequest);
    assertEquals(1, violations.size());

    final var violation = violations.iterator().next();
    assertEquals("title", violation.getPropertyPath().toString());
    assertEquals("must not be blank", violation.getMessage());
  }

  @Test
  void movieReviewRequest_WithTitleSetAboveMaxLength_ReturnsLengthError() {
    final var movieReviewRequest =
        MovieReviewRequest.builder()
            .title(StringUtils.leftPad("a", 256, 'a'))
            .movieYear(MOVIE_YEAR)
            .reviewScore(REVIEW_SCORE)
            .build();

    final var violations = validator.validate(movieReviewRequest);
    assertEquals(1, violations.size());

    final var violation = violations.iterator().next();
    assertEquals("title", violation.getPropertyPath().toString());
    assertEquals("length must be between 0 and 255", violation.getMessage());
  }

  @Test
  void movieReviewRequest_WithMovieYearSetToNull_ReturnsLengthError() {
    final var movieReviewRequest =
        MovieReviewRequest.builder()
            .title(TITLE)
            .movieYear(null)
            .reviewScore(REVIEW_SCORE)
            .build();

    final var violations = validator.validate(movieReviewRequest);
    assertEquals(1, violations.size());

    final var violation = violations.iterator().next();
    assertEquals("movieYear", violation.getPropertyPath().toString());
    assertEquals("must not be blank", violation.getMessage());
  }

  @Test
  void movieReviewRequest_WithMovieYearSetToBlank_ReturnsBlankError() {
    final var movieReviewRequest =
        MovieReviewRequest.builder()
            .title(TITLE)
            .movieYear("    ")
            .reviewScore(REVIEW_SCORE)
            .build();

    final var violations = validator.validate(movieReviewRequest);
    assertEquals(1, violations.size());

    final var violation = violations.iterator().next();
    assertEquals("movieYear", violation.getPropertyPath().toString());
    assertEquals("must not be blank", violation.getMessage());
  }

  @Test
  void movieReviewRequest_WithMovieYearSetBelowMin_ReturnsBlankError() {
    final var movieReviewRequest =
        MovieReviewRequest.builder()
            .title(TITLE)
            .movieYear("20")
            .reviewScore(REVIEW_SCORE)
            .build();

    final var violations = validator.validate(movieReviewRequest);
    assertEquals(1, violations.size());

    final var violation = violations.iterator().next();
    assertEquals("movieYear", violation.getPropertyPath().toString());
    assertEquals("length must be between 4 and 4", violation.getMessage());
  }

  @Test
  void movieReviewRequest_WithMovieYearSetAboveMax_ReturnsLengthError() {
    final var movieReviewRequest =
        MovieReviewRequest.builder()
            .title(TITLE)
            .movieYear("20000")
            .reviewScore(REVIEW_SCORE)
            .build();

    final var violations = validator.validate(movieReviewRequest);
    assertEquals(1, violations.size());

    final var violation = violations.iterator().next();
    assertEquals("movieYear", violation.getPropertyPath().toString());
    assertEquals("length must be between 4 and 4", violation.getMessage());
  }

  @Test
  void movieReviewRequest_WithReviewScoreSetToNull_ReturnsNullError() {
    final var movieReviewRequest =
        MovieReviewRequest.builder()
            .title(TITLE)
            .movieYear(MOVIE_YEAR)
            .reviewScore(null)
            .build();

    final var violations = validator.validate(movieReviewRequest);
    assertEquals(1, violations.size());

    final var violation = violations.iterator().next();
    assertEquals("reviewScore", violation.getPropertyPath().toString());
    assertEquals("must not be null", violation.getMessage());
  }

  @Test
  void movieReviewRequest_WithReviewScoreSetBelowMin_ReturnsSizeError() {
    final var movieReviewRequest =
        MovieReviewRequest.builder()
            .title(TITLE)
            .movieYear(MOVIE_YEAR)
            .reviewScore(-1)
            .build();

    final var violations = validator.validate(movieReviewRequest);
    assertEquals(1, violations.size());

    final var violation = violations.iterator().next();
    assertEquals("reviewScore", violation.getPropertyPath().toString());
    assertEquals("must be greater than or equal to 0", violation.getMessage());
  }

  @Test
  void movieReviewRequest_WithReviewScoreSetAboveMax_ReturnsSizeError() {
    final var movieReviewRequest =
        MovieReviewRequest.builder()
            .title(TITLE)
            .movieYear(MOVIE_YEAR)
            .reviewScore(101)
            .build();

    final var violations = validator.validate(movieReviewRequest);
    assertEquals(1, violations.size());

    final var violation = violations.iterator().next();
    assertEquals("reviewScore", violation.getPropertyPath().toString());
    assertEquals("must be less than or equal to 100", violation.getMessage());
  }
}