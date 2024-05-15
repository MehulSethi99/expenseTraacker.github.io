package com.moneymanager.service;

import com.moneymanager.model.OTP;

public interface OTPServiceInterface {

  void createOTP(String user);
  
  void deleteOTP(String email);

  OTP getOTP(String email);

  void updateUserValidity(String email, String validity);

  Boolean isValidUser(String email);
}