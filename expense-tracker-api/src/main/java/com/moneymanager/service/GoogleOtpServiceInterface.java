package com.moneymanager.service;

import java.io.IOException;
import com.google.zxing.WriterException;

public interface GoogleOtpServiceInterface {
  
  String generateSecretKey();

  String getTOTPCode(String secretKey);

  String getGoogleAuthenticatorBarCode(String secretKey, String account, String issuer);

  void createQRCode(String barCodeData, String filePath, int height, int width) throws WriterException, IOException;

}
