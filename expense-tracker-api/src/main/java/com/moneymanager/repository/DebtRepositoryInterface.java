package com.moneymanager.repository;

import java.util.List;

import com.moneymanager.exceptions.DebtBadRequest;
import com.moneymanager.exceptions.DebtResourceNotFoundException;
import com.moneymanager.model.Debt;

public interface DebtRepositoryInterface {
  
  List<Debt> findAll(String email, String month, Integer year) throws DebtResourceNotFoundException;

  List<Debt> findById(String email, String debtID) throws DebtResourceNotFoundException;

  void create(String email, String debtID, String title,  String description, Long date, Double amount, String month, Integer year) throws DebtBadRequest;

  void update(String email, String debtID, String title,  String description, Long date, Double amount, String month, Integer year) throws DebtBadRequest;
  
  void delete(String email, String debtID) throws DebtBadRequest;

  Double getTotalDebt(String email, String month, Integer year);

}