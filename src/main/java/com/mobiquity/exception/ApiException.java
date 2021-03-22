package com.mobiquity.exception;

/** Custom Exception class for packing-api.
 *
 */
public class ApiException extends Exception {

  public ApiException(String message, Exception e) {
    super(message, e);
  }

  public ApiException(String message) {
    super(message);
  }
}
