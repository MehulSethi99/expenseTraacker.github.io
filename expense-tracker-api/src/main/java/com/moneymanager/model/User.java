package com.moneymanager.model;

public class User {

  private String firstName;
  private String lastName;
  private String avatarUrl;
  private String email;
  private String password;
  private String preferedCurrency;
  private String googleauthkey;


  public User(String email, String firstName, String lastName, String password,String avatarUrl ,String preferedCurrency, String googleauthkey){
    this.email= email;
    this.firstName= firstName;
    this.lastName = lastName;
    this.avatarUrl = avatarUrl;
    this.preferedCurrency = preferedCurrency;
    this.password = password;
    this.googleauthkey = googleauthkey;
  }

  public String getEmail() {
    return email;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getAvatarUrl() {
    return avatarUrl;
  }

  public void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPreferedCurrency() {
    return preferedCurrency;
  }

  public void setPreferedCurrency(String preferedCurrency) {
    this.preferedCurrency = preferedCurrency;
  }

  public String getGoogleauthkey() {
    return googleauthkey;
  }

  public void setGoogleauthkey(String googleauthkey) {
    this.googleauthkey = googleauthkey;
  }
}