package com.moneymanager.repository;

import java.util.List;

import com.moneymanager.exceptions.LendingBadRequest;
import com.moneymanager.exceptions.LendingResourceNotFoundException;
import com.moneymanager.model.Lending;

public interface LendingRepositoryInterface {
  List<Lending> findAll(String email, String month, Integer year) throws LendingResourceNotFoundException;

  List<Lending> findById(String email, String lendingID) throws LendingResourceNotFoundException;

  void create(String email, String lendingID, String title,  String description, Long date, Double amount, String month, Integer year) throws LendingBadRequest;

  void update(String email, String lendingID, String title,  String description, Long date, Double amount, String month, Integer year) throws LendingBadRequest;
  
  void delete(String email, String lendingID) throws LendingBadRequest;

  Double getTotalLending(String email, String month, Integer year);
}