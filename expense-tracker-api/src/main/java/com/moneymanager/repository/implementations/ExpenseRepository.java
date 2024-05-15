package com.moneymanager.repository.implementations;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import com.moneymanager.exceptions.ExpenseBadRequest;
import com.moneymanager.exceptions.ExpenseResourceNotFoundException;
import com.moneymanager.model.Expense;
import com.moneymanager.repository.ExpenseRepositoryInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ExpenseRepository implements ExpenseRepositoryInterface {

  private static final String SQL_CREATE = "INSERT INTO exp_tracker_expense (expense_id, title, description, email, transaction_date, amount, month, year )"+
  " VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
  private static final String SQL_SUM_BY_EMAIL = "select sum(amount) from exp_tracker_expense WHERE EMAIL = ? and year=? and month =?";
  private static final String SQL_FIND_BY_ID = "SELECT expense_id, title, description, email, transaction_date, amount, month, year " +
  "FROM exp_tracker_expense WHERE expense_id = ?";
  private static final String SQL_FIND_BY_EMAIL =  "SELECT expense_id, title, description, email, transaction_date, amount, month, year " +
  "FROM exp_tracker_expense WHERE email = ? and year=? and month =?";
  private static final String SQL_DELETE_EXPENSE = "DELETE FROM exp_tracker_expense WHERE email = ? AND expense_id = ?";


  @Autowired
  JdbcTemplate jdbcTemplate;

  private RowMapper<Expense> expenseRowMapper = ((rs, rowNum) -> {
    return new Expense(rs.getString("email"),
            rs.getString("expense_id"),
            rs.getString("title"),
            rs.getString("description"),
            rs.getLong("transaction_date"),
            rs.getDouble("amount"),
            rs.getString("month"),
            rs.getInt("year"));
});

  @Override
  public List<Expense> findAll(String email,  String month, Integer year) throws ExpenseResourceNotFoundException {
    return jdbcTemplate.query(SQL_FIND_BY_EMAIL, new Object[] { email, year, month }, expenseRowMapper);
  }

  @Override
  public List<Expense> findById(String email, String expenseId) throws ExpenseResourceNotFoundException {
    return jdbcTemplate.query(SQL_FIND_BY_ID, new Object[] { email, expenseId }, expenseRowMapper);
  }

  @Override
  public void create(String email, String expenseID, String title, String description, Long date, Double amount,
      String month, Integer year) throws ExpenseBadRequest {
      try {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection ->{
          PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
          ps.setString(1, expenseID);
          ps.setString(2, title);
          ps.setString(3, description);
          ps.setString(4, email);
          ps.setLong(5, date);
          ps.setDouble(6, amount);
          ps.setString(7, month);
          ps.setInt(8, year);
          return ps;
        }, keyHolder);

      } catch (Exception e) {
        throw new ExpenseBadRequest(e.toString());
      }

  }

  @Override
  public void update(String email, String expenseID, String title, String description, Long date, Double amount,
      String month, Integer year) throws ExpenseBadRequest {
    
  }

  @Override
  public void delete(String email, String expenseID) throws ExpenseBadRequest {
    jdbcTemplate.update(SQL_DELETE_EXPENSE, new Object[] { email, expenseID });
  }

  @Override
  public Double getTotalExpense(String email, String month, Integer year) {
    return jdbcTemplate.queryForObject(SQL_SUM_BY_EMAIL, new Object[] { email, year, month }, Double.class);
  }
  
}