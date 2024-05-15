package com.moneymanager.service.implementations;

import java.util.regex.Pattern;

import com.moneymanager.exceptions.UserAuthException;
import com.moneymanager.model.User;
import com.moneymanager.repository.UserRepositoryInterface;
import com.moneymanager.service.AuthServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class AuthService implements AuthServiceInterface {

  @Autowired
  UserRepositoryInterface userRepository;

  @Override
  public User validateUser(String email, String password) throws UserAuthException {
    if(email != null) email = email.toLowerCase();
    return userRepository.findEmailAndPassword(email, password);
  }

  @Override
  public User registerUser(String firstName, String lastName, String email, String avatarUrl, String password,
      String preferedCurrency) throws UserAuthException {
        Integer count = userRepository.getCounterByEmail(email);
        if(count > 0)
          throw new UserAuthException("Email already in use");
        String userEmail = userRepository.create(firstName, lastName, email, avatarUrl,password, preferedCurrency);
    return userRepository.findById(userEmail);
  }

  @Override
  public User findById(String email) {
    return userRepository.findById(email);
  }
  
}