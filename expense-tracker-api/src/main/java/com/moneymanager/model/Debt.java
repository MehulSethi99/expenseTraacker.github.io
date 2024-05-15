package com.moneymanager.model;

public class Debt {
  private String email;
  private String debtID;
  private String title;
  private String description;
  private Long date;
  private Double amount;
  private String month;
  private Integer year;

  public Debt(String email, String debtID, String title,  String description, Long date, Double amount, String month, Integer year ){
    this.email=email;
    this.debtID = debtID;
    this.title = title;
    this.description = description;
    this.date = date;
    this.amount = amount;
    this.month = month;
    this.year = year;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getDebtID() {
    return debtID;
  }

  public void setDebtID(String debtID) {
    this.debtID = debtID;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Long getDate() {
    return date;
  }

  public void setDate(Long date) {
    this.date = date;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public String getMonth() {
    return month;
  }

  public void setMonth(String month) {
    this.month = month;
  }

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }
}