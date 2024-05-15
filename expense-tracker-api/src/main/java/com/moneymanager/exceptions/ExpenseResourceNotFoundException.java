package com.moneymanager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ExpenseResourceNotFoundException extends RuntimeException{
  public ExpenseResourceNotFoundException(String message){
    super(message);
  }
}