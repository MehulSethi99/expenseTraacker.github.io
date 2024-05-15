package com.moneymanager.service.implementations;

import java.util.List;

import com.moneymanager.exceptions.SavingsBadRequest;
import com.moneymanager.exceptions.SavingsResourceNotFoundException;
import com.moneymanager.model.Saving;
import com.moneymanager.repository.SavingsRepositoryInterface;
import com.moneymanager.service.SavingsServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SavingsService implements SavingsServiceInterface {

  @Autowired
  SavingsRepositoryInterface savingsRepository;

  @Override
  public List<Saving> fetchAllSavings(String email, String month, Integer year) {
    return savingsRepository.findAll(email, month, year);
  }

  @Override
  public List<Saving> fetchBySavingId(String email, String savingsID) throws SavingsResourceNotFoundException {
    savingsRepository.findById(email, savingsID);
    return null;
  }

  @Override
  public Saving addSaving(String email, String savingsID, String title, String description, Long date, Double amount,
      String month, Integer year) throws SavingsBadRequest {
    savingsRepository.create(email, savingsID, title, description, date, amount, month, year);
    return null;
  }

  @Override
  public void updateSaving() throws SavingsBadRequest {
    // TODO Auto-generated method stub

  }

  @Override
  public void removeSaving(String email, String savingsID) throws SavingsResourceNotFoundException {
    savingsRepository.delete(email, savingsID);
  }

  @Override
  public Double getTotalSaving(String email, String month, Integer year) {
    return savingsRepository.getTotalSaving(email, month, year);
  }
  
}