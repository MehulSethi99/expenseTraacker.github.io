package com.moneymanager.service.implementations;

import java.util.List;

import com.moneymanager.exceptions.LendingBadRequest;
import com.moneymanager.exceptions.LendingResourceNotFoundException;
import com.moneymanager.model.Lending;
import com.moneymanager.repository.LendingRepositoryInterface;
import com.moneymanager.service.LendingServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LendingService implements LendingServiceInterface {

  @Autowired
  LendingRepositoryInterface lendingRepository;

  @Override
  public List<Lending> fetchAllLendings(String email, String month, Integer year) {
    return lendingRepository.findAll(email, month, year);
  }

  @Override
  public List<Lending> fetchByLendingId(String email, String lendingID) throws LendingResourceNotFoundException {
    lendingRepository.findById(email, lendingID);
    return null;
  }

  @Override
  public Lending addLending(String email, String lendingID, String title, String description, Long date, Double amount,
      String month, Integer year) throws LendingBadRequest {
    lendingRepository.create(email, lendingID, title, description, date, amount, month, year);
    return null;
  }

  @Override
  public void updateLending() throws LendingBadRequest {
    // TODO Auto-generated method stub

  }

  @Override
  public void removeLending(String email, String lendingID) throws LendingResourceNotFoundException {
    lendingRepository.delete(email, lendingID);

  }

  @Override
  public Double getTotalLending(String email, String month, Integer year) {
    return lendingRepository.getTotalLending(email, month, year);
  }

}