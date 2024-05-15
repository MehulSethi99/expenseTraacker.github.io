package com.moneymanager.repository;

import java.util.List;

import com.moneymanager.exceptions.SavingsBadRequest;
import com.moneymanager.exceptions.SavingsResourceNotFoundException;
import com.moneymanager.model.Saving;

public interface SavingsRepositoryInterface {
  
  List<Saving> findAll(String email, String month, Integer year) throws SavingsResourceNotFoundException;

  List<Saving> findById(String email, String savingsId) throws SavingsResourceNotFoundException;

  Double getTotalSaving(String email, String month, Integer year);

  void create(String email, String savingsId, String title,  String description, Long date, Double amount, String month, Integer year) throws SavingsBadRequest;

  void update(String email, String savingsId, String title,  String description, Long date, Double amount, String month, Integer year) throws SavingsBadRequest;
  
  void delete(String email, String savingsId) throws SavingsBadRequest;

}