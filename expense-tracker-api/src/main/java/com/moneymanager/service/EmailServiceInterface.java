package com.moneymanager.service;

import javax.mail.MessagingException;

public interface EmailServiceInterface {
  void sendMail(String toEmail, String subject, String message) throws MessagingException;
}