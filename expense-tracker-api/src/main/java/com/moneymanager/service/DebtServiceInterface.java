package com.moneymanager.service;

import java.util.List;

import com.moneymanager.exceptions.DebtBadRequest;
import com.moneymanager.exceptions.DebtResourceNotFoundException;
import com.moneymanager.model.Debt;

public interface DebtServiceInterface {
  List<Debt> fetchAllDebts(String email, String month, Integer year);

  List<Debt> fetchByDebtId(String email, String debtID) throws DebtResourceNotFoundException;

  Debt addDebt(String email, String debtID, String title,  String description, Long date, Double amount, String month, Integer year) throws DebtBadRequest;

  void updateDebt() throws DebtBadRequest;

  void removeDebt(String email, String debtID) throws DebtResourceNotFoundException;

  Double getTotalDebt(String email, String month, Integer year);

}