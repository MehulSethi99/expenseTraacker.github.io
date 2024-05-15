package com.moneymanager.repository.implementations;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import com.moneymanager.exceptions.SavingsBadRequest;
import com.moneymanager.exceptions.SavingsResourceNotFoundException;
import com.moneymanager.model.Saving;
import com.moneymanager.repository.SavingsRepositoryInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class SavingsRepository implements SavingsRepositoryInterface {

  private static final String SQL_CREATE = "INSERT INTO exp_tracker_savings (saving_id, title, description, email, transaction_date, amount, month, year )"+
  " VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
  private static final String SQL_SUM_BY_EMAIL = "select sum(amount) from exp_tracker_savings WHERE EMAIL = ? and year=? and month =?";
  private static final String SQL_FIND_BY_ID = "SELECT saving_id, title, description, email, transaction_date, amount, month, year " +
  "FROM exp_tracker_savings WHERE saving_id = ?";
  private static final String SQL_FIND_BY_EMAIL =  "SELECT saving_id, title, description, email, transaction_date, amount, month, year " +
  "FROM exp_tracker_savings WHERE email = ? and year=? and month =?";
  private static final String SQL_DELETE_SAVING = "DELETE FROM exp_tracker_savings WHERE email = ? AND saving_id = ?";

  @Autowired
  JdbcTemplate jdbcTemplate;

  private RowMapper<Saving> savingsRowMapper = ((rs, rowNum) -> {
    return new Saving(rs.getString("email"),
            rs.getString("saving_id"),
            rs.getString("title"),
            rs.getString("description"),
            rs.getLong("transaction_date"),
            rs.getDouble("amount"),
            rs.getString("month"),
            rs.getInt("year"));
});
  
  @Override
  public List<Saving> findAll(String email, String month, Integer year ) throws SavingsResourceNotFoundException {
    return jdbcTemplate.query(SQL_FIND_BY_EMAIL, new Object[] { email, year, month }, savingsRowMapper);
  }

  @Override
  public List<Saving> findById(String email, String savingsId) throws SavingsResourceNotFoundException {
    return jdbcTemplate.query(SQL_FIND_BY_ID, new Object[] { email, savingsId }, savingsRowMapper);
  }

  @Override
  public void create(String email, String savingsId, String title, String description, Long date, Double amount,
      String month, Integer year) throws SavingsBadRequest {
        try {
          KeyHolder keyHolder = new GeneratedKeyHolder();
          jdbcTemplate.update(connection ->{
            PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, savingsId);
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
          throw new SavingsBadRequest(e.toString());
        }
  }

  @Override
  public void update(String email, String savingsId, String title, String description, Long date, Double amount,
      String month, Integer year) throws SavingsBadRequest {
    // TODO Auto-generated method stub

  }

  @Override
  public void delete(String email, String savingsId) throws SavingsBadRequest {
    jdbcTemplate.update(SQL_DELETE_SAVING, new Object[] { email, savingsId });
  }

  @Override
  public Double getTotalSaving(String email, String month, Integer year) {
    return jdbcTemplate.queryForObject(SQL_SUM_BY_EMAIL, new Object[] { email, year, month }, Double.class);
  }
  
}