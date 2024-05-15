package com.moneymanager.controller;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.moneymanager.model.Saving;
import com.moneymanager.service.SavingsServiceInterface;

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
@RequestMapping("/api/moneymanager/savings")
public class SavingsController {
  
  @Autowired
  SavingsServiceInterface savingsService;

  @PostMapping()
  public ResponseEntity<Map<String, Boolean>> addSaving(HttpServletRequest request,
      @RequestBody Map<String, Object> saving) {
    
    String email = (String) request.getAttribute("email");
    String savingsID = new StringBuilder().append(Instant.now().getEpochSecond()).toString();
    String title = (String) saving.get("title");
    String description = (String) saving.get("description");
    Long date = Instant.now().getEpochSecond();
    Double amount = Double.parseDouble(saving.get("amount").toString());
    String month = (String) saving.get("month");
    Integer year = (Integer) saving.get("year");
    
    savingsService.removeSaving(email, savingsID);
    savingsService.addSaving(email, savingsID, title, description, date, amount, month, year);
    Map<String, Boolean> map = new HashMap<>();
    map.put("success", true);
    return new ResponseEntity<>(map, HttpStatus.OK);
  }


  @PutMapping()
  public ResponseEntity<Map<String, Boolean>> updateSaving(HttpServletRequest request,
      @RequestBody Map<String, Object> saving){

    String email = (String) saving.get("email");
    String savingsID = (String) saving.get("savingID");
    String title = (String) saving.get("title");
    String description = (String) saving.get("description");
    Long date = Long.valueOf(((Integer) saving.get("date")).longValue());
    Double amount = (Double) saving.get("amount");
    String month = (String) saving.get("month");
    Integer year = (Integer) saving.get("year");
    
    savingsService.removeSaving(email, savingsID);
    savingsService.addSaving(email, savingsID, title, description, date, amount, month, year);
    Map<String, Boolean> map = new HashMap<>();
    map.put("success", true);
    return new ResponseEntity<>(map, HttpStatus.OK);
  }

  @GetMapping("/{month}/{year}")
  public ResponseEntity<List<Saving>> getAllSaving(HttpServletRequest request,
  @PathVariable("month") String month, @PathVariable("year") Integer year){
    String email = (String) request.getAttribute("email");
    List<Saving> allSavings= savingsService.fetchAllSavings(email, month, year);
    return new ResponseEntity<>(allSavings,HttpStatus.OK);
  }

  @DeleteMapping("/{savingID}")
  public ResponseEntity<Map<String, Boolean>> deleteSaving(HttpServletRequest request, @PathVariable("savingID") String savingID){
    String email = (String) request.getAttribute("email");
    savingsService.removeSaving(email, savingID);
    Map<String, Boolean> map = new HashMap<>();
    map.put("status", true);
    return new ResponseEntity<>(map, HttpStatus.OK);
  }

  @PostMapping("/saving-total")
  public ResponseEntity<Map<String, Double>> getTotalSaving(HttpServletRequest request, @RequestBody Map<String, Object> saving){
    String email = (String) request.getAttribute("email");
    String month = (String) saving.get("month");
    Integer year = (Integer) saving.get("year");
    Double totalSum =savingsService.getTotalSaving(email, month, year);
    Map<String, Double> map = new HashMap<>();
    map.put("total", totalSum);
    return new ResponseEntity<>(map, HttpStatus.OK);
  }
}