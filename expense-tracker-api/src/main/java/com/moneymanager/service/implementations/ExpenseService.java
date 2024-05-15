package com.moneymanager.service.implementations;

import java.util.List;

import com.moneymanager.exceptions.ExpenseBadRequest;
import com.moneymanager.exceptions.ExpenseResourceNotFoundException;
import com.moneymanager.model.Expense;
import com.moneymanager.repository.ExpenseRepositoryInterface;
import com.moneymanager.service.ExpenseServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ExpenseService implements ExpenseServiceInterface {

  @Autowired
  ExpenseRepositoryInterface expenseRepository;

  @Override
  public List<Expense> fetchAllExpenses(String email, String month, Integer year) {
    return expenseRepository.findAll(email, month, year);
  }

  @Override
  public List<Expense> fetchByExpenseId(String email, String expenseId) throws ExpenseResourceNotFoundException {
    expenseRepository.findById(email, expenseId);
    return null;
  }

  @Override
  public Expense addExpense(String email, String expenseID, String title, String description, Long date, Double amount,
      String month, Integer year) throws ExpenseBadRequest {
    expenseRepository.create(email, expenseID, title, description, date, amount, month, year);
    return null;
  }

  @Override
  public void updateExpense() throws ExpenseBadRequest {
    // TODO Auto-generated method stub

  }

  @Override
  public void removeExpense(String email, String expenseID) throws ExpenseResourceNotFoundException {
    expenseRepository.delete(email, expenseID);

  }

  @Override
  public Double getTotalExpense(String email, String month, Integer year) {
    return expenseRepository.getTotalExpense(email, month, year);
  }
  
}