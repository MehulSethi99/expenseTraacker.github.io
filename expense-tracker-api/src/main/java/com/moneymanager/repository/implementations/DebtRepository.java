package com.moneymanager.repository.implementations;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import com.moneymanager.exceptions.DebtBadRequest;
import com.moneymanager.exceptions.DebtResourceNotFoundException;
import com.moneymanager.model.Debt;
import com.moneymanager.repository.DebtRepositoryInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class DebtRepository implements DebtRepositoryInterface {

  private static final String SQL_CREATE = "INSERT INTO exp_tracker_debts (debt_id, title, description, email, transaction_date, amount, month, year )"+
  " VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
  private static final String SQL_SUM_BY_EMAIL = "select sum(amount) from exp_tracker_debts WHERE EMAIL = ? and year=? and month =?";
  private static final String SQL_FIND_BY_ID = "SELECT debt_id, title, description, email, transaction_date, amount, month, year " +
  "FROM exp_tracker_debts WHERE debt_id = ?";
  private static final String SQL_FIND_BY_EMAIL =  "SELECT debt_id, title, description, email, transaction_date, amount, month, year " +
  "FROM exp_tracker_debts WHERE email = ? and year=? and month =?";
  private static final String SQL_DELETE_DEBT = "DELETE FROM exp_tracker_debts WHERE email = ? AND debt_id = ?";

  @Autowired
  JdbcTemplate jdbcTemplate;

  private RowMapper<Debt> lendingRowMapper = ((rs, rowNum) -> {
    return new Debt(rs.getString("email"),
            rs.getString("debt_id"),
            rs.getString("title"),
            rs.getString("description"),
            rs.getLong("transaction_date"),
            rs.getDouble("amount"),
            rs.getString("month"),
            rs.getInt("year"));
    });

  @Override
  public List<Debt> findAll(String email, String month, Integer year) throws DebtResourceNotFoundException {
    return jdbcTemplate.query(SQL_FIND_BY_EMAIL, new Object[] { email, year, month }, lendingRowMapper);
  }

  @Override
  public List<Debt> findById(String email, String debtID) throws DebtResourceNotFoundException {
    return jdbcTemplate.query(SQL_FIND_BY_ID, new Object[] { email, debtID }, lendingRowMapper);
  }

  @Override
  public void create(String email, String debtID, String title, String description, Long date, Double amount,
      String month, Integer year) throws DebtBadRequest {
        try {
          KeyHolder keyHolder = new GeneratedKeyHolder();
          jdbcTemplate.update(connection ->{
            PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, debtID);
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
          throw new DebtBadRequest(e.toString());
        }

  }

  @Override
  public void update(String email, String debtID, String title, String description, Long date, Double amount,
      String month, Integer year) throws DebtBadRequest {
    // TODO Auto-generated method stub

  }

  @Override
  public void delete(String email, String debtID) throws DebtBadRequest {
    jdbcTemplate.update(SQL_DELETE_DEBT, new Object[] { email, debtID });
  }

  @Override
  public Double getTotalDebt(String email, String month, Integer year) {
    return jdbcTemplate.queryForObject(SQL_SUM_BY_EMAIL, new Object[] { email, year, month }, Double.class);
  }
  
}