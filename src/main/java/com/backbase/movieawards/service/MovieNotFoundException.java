package com.backbase.movieawards.service;

public class MovieNotFoundException extends RuntimeException {

  public MovieNotFoundException(final String message) {
    super(message);
  }
}
