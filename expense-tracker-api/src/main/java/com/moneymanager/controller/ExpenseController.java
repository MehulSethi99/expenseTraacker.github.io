package com.moneymanager.controller;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.moneymanager.model.Expense;
import com.moneymanager.service.ExpenseServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/moneymanager/expense")
public class ExpenseController {

  @Autowired
  ExpenseServiceInterface expenseService;

  @PostMapping()
  public ResponseEntity<Map<String, Boolean>> addExpense(HttpServletRequest request,
      @RequestBody Map<String, Object> expense) {
    
    String email = (String) request.getAttribute("email");
    String expenseID = new StringBuilder().append(Instant.now().getEpochSecond()).toString();
    String title = (String) expense.get("title");
    String description = (String) expense.get("description");
    Long date = Instant.now().getEpochSecond();
    Double amount = Double.parseDouble(expense.get("amount").toString());
    String month = (String) expense.get("month");
    Integer year = (Integer) expense.get("year");

    expenseService.addExpense(email, expenseID, title, description, date, amount, month, year);
    Map<String, Boolean> map = new HashMap<>();
    map.put("success", true);
    return new ResponseEntity<>(map, HttpStatus.OK);
  }

  @PutMapping()
  public ResponseEntity<Map<String, Boolean>> updateExpense(HttpServletRequest request,
      @RequestBody Map<String, Object> expense){
        

    String email = (String) request.getAttribute("email");
    String expenseID = (String) expense.get("expenseID");
    String title = (String) expense.get("title");
    String description = (String) expense.get("description");
    Long date = Long.valueOf(((Integer) expense.get("date")).longValue());
    Double amount = (Double) expense.get("amount");
    String month = (String) expense.get("month");
    Integer year = (Integer) expense.get("year");
    
    expenseService.removeExpense(email, expenseID);
    expenseService.addExpense(email, expenseID, title, description, date, amount, month, year);
    Map<String, Boolean> map = new HashMap<>();
    map.put("success", true);
    return new ResponseEntity<>(map, HttpStatus.OK);
  }

  @GetMapping("/{month}/{year}")
  public ResponseEntity<List<Expense>> getAllExpense(HttpServletRequest request, 
  @PathVariable("month") String month, @PathVariable("year") Integer year){
    String email = (String) request.getAttribute("email");
    List<Expense> allExpenss= expenseService.fetchAllExpenses(email, month, year);
    return new ResponseEntity<>(allExpenss,HttpStatus.OK);
  }

  @DeleteMapping("/{expenseID}")
  public ResponseEntity<Map<String, Boolean>> deleteExpense(HttpServletRequest request, @PathVariable("expenseID") String expenseID){
    String email = (String) request.getAttribute("email");
    expenseService.removeExpense(email, expenseID);
    Map<String, Boolean> map = new HashMap<>();
    map.put("status", true);
    return new ResponseEntity<>(map, HttpStatus.OK);
  }

  @PostMapping("/expense-total")
  public ResponseEntity<Map<String, Double>> getTotalExpense(HttpServletRequest request, @RequestBody Map<String, Object> expense){
    String email = (String) request.getAttribute("email");
    String month = (String) expense.get("month");
    Integer year = (Integer) expense.get("year");
    Double totalSum =expenseService.getTotalExpense(email, month, year);
    Map<String, Double> map = new HashMap<>();
    map.put("total", totalSum);
    return new ResponseEntity<>(map, HttpStatus.OK);
  }

}