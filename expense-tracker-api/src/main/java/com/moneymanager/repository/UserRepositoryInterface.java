package com.moneymanager.repository;

import com.moneymanager.exceptions.UserAuthException;
import com.moneymanager.model.User;

public interface UserRepositoryInterface {
  String create(String firstName, String lastName, String email, String avatarUrl, String password,
  String preferedCurrency) throws UserAuthException;

  User findEmailAndPassword(String email, String password) throws UserAuthException;

  Integer getCounterByEmail(String email);

  User findById(String email);
}