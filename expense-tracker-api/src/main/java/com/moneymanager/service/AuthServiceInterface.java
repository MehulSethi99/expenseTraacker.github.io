package com.moneymanager.service;

import com.moneymanager.exceptions.UserAuthException;
import com.moneymanager.model.User;

public interface AuthServiceInterface {
  
  User validateUser(String email, String password) throws UserAuthException;

  User registerUser( String firstName, String lastName, String email, String avatarUrl,String password, String preferedCurrency) throws UserAuthException;

  User findById(String email);
}