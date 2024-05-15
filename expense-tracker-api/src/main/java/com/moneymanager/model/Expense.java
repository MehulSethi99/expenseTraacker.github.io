package com.moneymanager.model;

public class Expense {
  private String email;
  private String expenseID;
  private String title;
  private String description;
  private Long date;
  private Double amount;
  private String month;
  private Integer year;

  public Expense(String email, String expenseID, String title,  String description, Long date, Double amount, String month, Integer year ){
    this.email=email;
    this.expenseID = expenseID;
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

  public String getExpenseID() {
    return expenseID;
  }

  public void setExpenseID(String expenseID) {
    this.expenseID = expenseID;
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