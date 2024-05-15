package com.moneymanager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DebtBadRequest extends RuntimeException{
  public DebtBadRequest(String message){
    super(message);
  }
}
