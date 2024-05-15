package com.moneymanager.controller;

import javax.servlet.http.HttpServletRequest;

import com.moneymanager.model.User;
import com.moneymanager.service.AuthServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/moneymanager/profile")
public class ProfileController {
  
  @Autowired
  AuthServiceInterface authService;

  @GetMapping()
  public ResponseEntity<User> getAllExpense(HttpServletRequest request){
    String email = (String) request.getAttribute("email");
    User user= authService.findById(email);
    user.setPassword("**************Encrypted****************");
    return new ResponseEntity<>(user,HttpStatus.OK);
  }
}