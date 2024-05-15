package com.moneymanager.model;

public class OTP {
  
  private String otp;
  private String email;
  private String expiryTime;

  public OTP( String email, String expiryTime, String otp){
    this.email= email;
    this.expiryTime= expiryTime;
    this.otp=otp;
  }

  public String getOtp() {
    return otp;
  }

  public void setOtp(String otp) {
    this.otp = otp;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getExpiryTime() {
    return expiryTime;
  }

  public void setExpiryTime(String expiryTime) {
    this.expiryTime = expiryTime;
  }
}