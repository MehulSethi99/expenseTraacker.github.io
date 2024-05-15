package com.moneymanager.service.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.moneymanager.service.EmailServiceInterface;

import org.springframework.mail.javamail.MimeMessageHelper;

@Service
public class EmailService implements EmailServiceInterface{

  @Autowired
  private JavaMailSender javaMailSender;

  public EmailService(JavaMailSender javaMailSender) {
    this.javaMailSender = javaMailSender;
  }

  public void sendMail(String toEmail, String subject, String message) throws MessagingException {

    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
 
    helper.setTo(toEmail);
    helper.setSubject(subject);
    helper.setFrom("nikil2021998@gmail.com");
    helper.setText(message, true);
    
    javaMailSender.send(mimeMessage);
  }
}