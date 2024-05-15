package com.moneymanager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LendingResourceNotFoundException extends RuntimeException{
  public LendingResourceNotFoundException(String message){
    super(message);
  }
}