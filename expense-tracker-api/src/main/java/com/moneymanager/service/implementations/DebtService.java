package com.moneymanager.service.implementations;

import java.util.List;

import com.moneymanager.exceptions.DebtBadRequest;
import com.moneymanager.exceptions.DebtResourceNotFoundException;
import com.moneymanager.model.Debt;
import com.moneymanager.repository.DebtRepositoryInterface;
import com.moneymanager.service.DebtServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DebtService implements DebtServiceInterface {

  @Autowired
  DebtRepositoryInterface debtRepository;

  @Override
  public List<Debt> fetchAllDebts(String email, String month, Integer year) {
    return debtRepository.findAll(email, month, year);
  }

  @Override
  public List<Debt> fetchByDebtId(String email, String debtID) throws DebtResourceNotFoundException {
    debtRepository.findById(email, debtID);
    return null;
  }

  @Override
  public Debt addDebt(String email, String debtID, String title, String description, Long date, Double amount,
      String month, Integer year) throws DebtBadRequest {
    debtRepository.create(email, debtID, title, description, date, amount, month, year);
    return null;
  }

  @Override
  public void updateDebt() throws DebtBadRequest {
    // TODO Auto-generated method stub

  }

  @Override
  public void removeDebt(String email, String debtID) throws DebtResourceNotFoundException {
    debtRepository.delete(email, debtID);

  }

  @Override
  public Double getTotalDebt(String email, String month, Integer year) {
    return debtRepository.getTotalDebt(email, month, year);
  }
  
}