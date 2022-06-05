package com.backbase.movieawards.api;

import com.backbase.movieawards.model.MovieAwardsErrorResponse;
import com.backbase.movieawards.service.MovieAwardsErrorResponseAssembler;
import com.backbase.movieawards.service.MovieNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MovieAwardsAdvice {

  private final MovieAwardsErrorResponseAssembler assembler;

  @ExceptionHandler(MovieNotFoundException.class)
  public ResponseEntity<String> movieNotFoundExceptionHandler(final MovieNotFoundException exception) {
    return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<List<MovieAwardsErrorResponse>> handleConstraintViolationException(
      final ConstraintViolationException exception) {
    final var body = getErrors(exception);
    return ResponseEntity.badRequest().body(body);
  }

  private List<MovieAwardsErrorResponse> getErrors(final ConstraintViolationException exception) {
    final var violations = exception.getConstraintViolations();

    return violations.stream()
        .map(violation -> assembler.assemble(((PathImpl)violation.getPropertyPath()).getLeafNode().getName(), violation.getMessage()))
        .collect(Collectors.toList());
  }

}
