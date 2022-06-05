package com.backbase.movieawards.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.backbase.movieawards.model.MovieAwardsErrorResponse;
import com.backbase.movieawards.service.MovieAwardsErrorResponseAssembler;
import com.backbase.movieawards.service.MovieNotFoundException;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
class MovieAwardsAdviceTest {

  private static final String MESSAGE = "test exception";
  private static final String FIELD1 = "field1";
  private static final String ERROR_MESSAGE1 = "message1";
  private static final String FIELD2 = "field2";
  private static final String ERROR_MESSAGE2 = "message2";

  @Mock
  private MovieNotFoundException movieNotFoundexception;
  @Mock
  private ConstraintViolationException constraintViolationException;
  @Mock
  private ConstraintViolation<?> constraintViolation1;
  @Mock
  private ConstraintViolation<?> constraintViolation2;

  @Mock
  private MovieAwardsErrorResponseAssembler assembler;
  @Mock
  private MovieAwardsErrorResponse errorResponse1;
  @Mock
  private MovieAwardsErrorResponse errorResponse2;

  private Set<ConstraintViolation<?>> violations;
  private PathImpl pathImpl1;
  private PathImpl pathImpl2;

  private MovieAwardsAdvice subject;

  @BeforeEach
  void onBeforeEach() {
    violations = Set.of(constraintViolation1, constraintViolation2);
    subject = new MovieAwardsAdvice(assembler);
    pathImpl1 = PathImpl.createPathFromString(FIELD1);
    pathImpl2 = PathImpl.createPathFromString(FIELD2);
  }

  @Test
  void movieNotFoundExceptionHandler_WithMovieNotFoundException_ReturnsNotFoundWithMessage() {
    when(movieNotFoundexception.getMessage()).thenReturn(MESSAGE);
    final var response = subject.movieNotFoundExceptionHandler(movieNotFoundexception);
    assertNotNull(response);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals(MESSAGE, response.getBody());
  }

  @Test
  void handleConstraintViolationException_WithConstraintViolationException_ReturnsBadRequestAndListOfErrors() {
    when(constraintViolationException.getConstraintViolations()).thenReturn(violations);
    when(constraintViolation1.getPropertyPath()).thenReturn(pathImpl1);
    when(constraintViolation2.getPropertyPath()).thenReturn(pathImpl2);
    when(constraintViolation1.getMessage()).thenReturn(ERROR_MESSAGE1);
    when(constraintViolation2.getMessage()).thenReturn(ERROR_MESSAGE2);
    when(assembler.assemble(FIELD1, ERROR_MESSAGE1)).thenReturn(errorResponse1);
    when(assembler.assemble(FIELD2, ERROR_MESSAGE2)).thenReturn(errorResponse2);

    final var response = subject.handleConstraintViolationException(constraintViolationException);
    assertNotNull(response);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    final var body = response.getBody();
    assertNotNull(body);
    assertEquals(2, body.size());

    assertTrue(body.contains(errorResponse1));
    assertTrue(body.contains(errorResponse2));
  }
}