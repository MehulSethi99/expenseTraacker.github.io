package com.moneymanager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class OTPResourceNotFoundException extends RuntimeException {
  public OTPResourceNotFoundException(String message){
    super(message);
  }
}