package com.moneymanager.model;

public class Saving {
  private String email;
  private String savingsID;
  private String title;
  private String description;
  private Long date;
  private Double amount;
  private String month;
  private Integer year;

  public Saving(String email, String savingsID, String title,  String description, Long date, Double amount, String month, Integer year ){
    this.email=email;
    this.savingsID = savingsID;
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

  public String getSavingsID() {
    return savingsID;
  }

  public void setSavingsID(String savingsID) {
    this.savingsID = savingsID;
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