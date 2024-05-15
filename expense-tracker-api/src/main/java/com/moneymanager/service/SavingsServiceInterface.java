package com.moneymanager.service;

import java.util.List;

import com.moneymanager.exceptions.SavingsBadRequest;
import com.moneymanager.exceptions.SavingsResourceNotFoundException;
import com.moneymanager.model.Saving;

public interface SavingsServiceInterface {
  
  List<Saving> fetchAllSavings(String email, String month, Integer year);

  List<Saving> fetchBySavingId(String email, String savingsID) throws SavingsResourceNotFoundException;

  public Double getTotalSaving(String email, String month, Integer year);

  Saving addSaving(String email, String savingsID, String title,  String description, Long date, Double amount, String month, Integer year) throws SavingsBadRequest;

  void updateSaving() throws SavingsBadRequest;

  void removeSaving(String email, String savingsID) throws SavingsResourceNotFoundException;

}