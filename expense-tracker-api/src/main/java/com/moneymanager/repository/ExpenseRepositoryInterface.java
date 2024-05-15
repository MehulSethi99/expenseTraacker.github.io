package com.moneymanager.repository;

import java.util.List;

import com.moneymanager.exceptions.ExpenseBadRequest;
import com.moneymanager.exceptions.ExpenseResourceNotFoundException;
import com.moneymanager.model.Expense;

public interface ExpenseRepositoryInterface {

  List<Expense> findAll(String email, String month, Integer year) throws ExpenseResourceNotFoundException;

  List<Expense> findById(String email, String expenseId) throws ExpenseResourceNotFoundException;

  Double getTotalExpense(String email, String month, Integer year);

  void create(String email, String expenseID, String title,  String description, Long date, Double amount, String month, Integer year) throws ExpenseBadRequest;

  void update(String email, String expenseID, String title,  String description, Long date, Double amount, String month, Integer year) throws ExpenseBadRequest;
  
  void delete(String email, String expenseID) throws ExpenseBadRequest;

}