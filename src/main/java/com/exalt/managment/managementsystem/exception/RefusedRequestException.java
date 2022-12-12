package com.exalt.managment.managementsystem.exception;

/**
 * It's thrown when user request memory more than the server's maximum memory
 */
public class RefusedRequestException extends Exception {

  public RefusedRequestException(String s) {
    super(s);
  }
}
