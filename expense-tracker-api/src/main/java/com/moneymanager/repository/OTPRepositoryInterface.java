package com.moneymanager.repository;

import com.moneymanager.exceptions.OTPBadRequest;
import com.moneymanager.exceptions.OTPResourceNotFoundException;
import com.moneymanager.model.OTP;

public interface OTPRepositoryInterface {
  
  void createOTP(String user) throws OTPBadRequest;
  
  void deleteOTP(String email);

  OTP getOTP(String email) throws OTPResourceNotFoundException;

  void updateUserValidity(String email, String validity);

  Boolean isValidUser(String email) throws OTPResourceNotFoundException;
}