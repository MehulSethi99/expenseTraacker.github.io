package com.moneymanager.controller;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.moneymanager.model.Lending;
import com.moneymanager.service.LendingServiceInterface;

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
@RequestMapping("/api/moneymanager/lending")
public class LendingController {
  
  @Autowired
  LendingServiceInterface lendingService;

  @PostMapping()
  public ResponseEntity<Map<String, Boolean>> addLending(HttpServletRequest request,
      @RequestBody Map<String, Object> lending) {
    
    String email = (String) request.getAttribute("email");
    String lendingID = new StringBuilder().append(Instant.now().getEpochSecond()).toString();
    String title = (String) lending.get("title");
    String description = (String) lending.get("description");
    Long date = Instant.now().getEpochSecond();
    Double amount = Double.parseDouble(lending.get("amount").toString());
    String month = (String) lending.get("month");
    Integer year = (Integer) lending.get("year");

    lendingService.addLending(email, lendingID, title, description, date, amount, month, year);
    Map<String, Boolean> map = new HashMap<>();
    map.put("success", true);
    return new ResponseEntity<>(map, HttpStatus.OK);
  }

  @PutMapping()
  public ResponseEntity<Map<String, Boolean>> updateLending(HttpServletRequest request,
      @RequestBody Map<String, Object> lending){

    String email = (String) lending.get("email");
    String lendingID = (String) lending.get("lendingID");
    String title = (String) lending.get("title");
    String description = (String) lending.get("description");
    Long date = Long.valueOf(((Integer) lending.get("date")).longValue());
    Double amount = (Double) lending.get("amount");
    String month = (String) lending.get("month");
    Integer year = (Integer) lending.get("year");
    
    lendingService.removeLending(email, lendingID);
    lendingService.addLending(email, lendingID, title, description, date, amount, month, year);
    Map<String, Boolean> map = new HashMap<>();
    map.put("success", true);
    return new ResponseEntity<>(map, HttpStatus.OK);
  }

  @GetMapping("/{month}/{year}")
  public ResponseEntity<List<Lending>> getAllLending(HttpServletRequest request, 
  @PathVariable("month") String month, @PathVariable("year") Integer year){
    String email = (String) request.getAttribute("email");
    List<Lending> allLendings= lendingService.fetchAllLendings(email,month, year);
    return new ResponseEntity<>(allLendings,HttpStatus.OK);
  }

  @DeleteMapping("/{lendingID}")
  public ResponseEntity<Map<String, Boolean>> deleteLending(HttpServletRequest request, @PathVariable("lendingID") String lendingID){
    String email = (String) request.getAttribute("email");
    lendingService.removeLending(email, lendingID);
    Map<String, Boolean> map = new HashMap<>();
    map.put("status", true);
    return new ResponseEntity<>(map, HttpStatus.OK);
  }
  
  @PostMapping("/lend-total")
  public ResponseEntity<Map<String, Double>> getTotalLending(HttpServletRequest request, @RequestBody Map<String, Object> lend){
    String email = (String) request.getAttribute("email");
    String month = (String) lend.get("month");
    Integer year = (Integer) lend.get("year");
    Double totalSum =lendingService.getTotalLending(email, month, year);
    Map<String, Double> map = new HashMap<>();
    map.put("total", totalSum);
    return new ResponseEntity<>(map, HttpStatus.OK);
  }
}