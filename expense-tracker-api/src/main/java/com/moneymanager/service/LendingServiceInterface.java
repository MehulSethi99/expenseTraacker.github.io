package com.moneymanager.service;

import java.util.List;

import com.moneymanager.exceptions.LendingBadRequest;
import com.moneymanager.exceptions.LendingResourceNotFoundException;
import com.moneymanager.model.Lending;

public interface LendingServiceInterface {
  
  List<Lending> fetchAllLendings(String email, String month, Integer year);

  List<Lending> fetchByLendingId(String email, String lendingID) throws LendingResourceNotFoundException;

  Lending addLending(String email, String lendingID, String title,  String description, Long date, Double amount, String month, Integer year) throws LendingBadRequest;

  void updateLending() throws LendingBadRequest;

  void removeLending(String email, String lendingID) throws LendingResourceNotFoundException;

  Double getTotalLending(String email, String month, Integer year);

}