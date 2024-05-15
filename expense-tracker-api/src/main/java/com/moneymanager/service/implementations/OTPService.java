package com.moneymanager.service.implementations;

import com.moneymanager.model.OTP;
import com.moneymanager.repository.OTPRepositoryInterface;
import com.moneymanager.service.OTPServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class OTPService implements OTPServiceInterface {

  @Autowired
  OTPRepositoryInterface otpRepository;

  @Override
  public void createOTP(String user) {
    otpRepository.createOTP(user);
  }

  @Override
  public void deleteOTP(String email) {
    otpRepository.deleteOTP(email);

  }

  @Override
  public OTP getOTP(String email) {
    return otpRepository.getOTP(email);
  }

  @Override
  public Boolean isValidUser(String email) {
    return otpRepository.isValidUser(email);
  }

  @Override
  public void updateUserValidity(String email, String validity) {
    otpRepository.updateUserValidity(email, validity);
  }

}