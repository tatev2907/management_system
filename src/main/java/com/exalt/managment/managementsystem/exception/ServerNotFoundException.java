package com.exalt.managment.managementsystem.exception;

/**
 * The Server not found exception is thrown when user tries to access non-existing server/severs.
 */
public class ServerNotFoundException extends Exception {

  public ServerNotFoundException(String s) {
    super(s);
  }
}
