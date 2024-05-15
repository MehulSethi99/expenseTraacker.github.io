package com.moneymanager.repository.implementations;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.moneymanager.exceptions.OTPBadRequest;
import com.moneymanager.exceptions.OTPResourceNotFoundException;
import com.moneymanager.model.OTP;
import com.moneymanager.repository.OTPRepositoryInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@Repository
public class OTPRepository implements OTPRepositoryInterface {

  private static final String ADD_OTP = "INSERT INTO otp_service (email, otpexpirytime, otp ) VALUES(?, ?, ?)";

  private static final String UPDATE_USER_VALIDITY_STATUS = "UPDATE exp_tracker_users SET is_valid = ? WHERE email = ?";

  private static final String CHECK_VALIDITY_STATUS = "SELECT is_valid FROM exp_tracker_users WHERE email = ?";

  private static final String SQL_DELETE_OTP = "DELETE FROM otp_service WHERE email = ?";

  private static final String GET_OTP = "SELECT email, otpexpirytime, otp FROM otp_service WHERE email=? ";

  @Autowired
  JdbcTemplate jdbcTemplate;

  private RowMapper<OTP> otpRowMapper = ((rs, rowNum) -> {
    return new OTP(
      rs.getString("email"), 
      rs.getString("otpexpirytime"), 
      rs.getString("otp"));
  });

  @Override
  public void createOTP(String user) throws OTPBadRequest {
    String otp = new DecimalFormat("000000").format(new Random().nextInt(999999));
    String expiryTime = String.valueOf((System.currentTimeMillis() / 1000) + 240);
    try {
      KeyHolder keyHolder = new GeneratedKeyHolder();
      jdbcTemplate.update(connection ->{
        PreparedStatement ps = connection.prepareStatement(ADD_OTP, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, user);
        ps.setString(2, expiryTime);
        ps.setString(3, otp);
        return ps;
      }, keyHolder);

    } catch (Exception e) {
      throw new OTPBadRequest(e.toString());
    }

  }

  @Override
  public void deleteOTP(String email) throws OTPResourceNotFoundException {
    jdbcTemplate.update(SQL_DELETE_OTP, new Object[] { email });
  }

  @Override
  public OTP getOTP(String email) {
    List<OTP> otps = jdbcTemplate.query(GET_OTP, new Object[] { email }, otpRowMapper);
    return otps.get(0);
  }

  @Override
  public void updateUserValidity(String email, String validity) {
    jdbcTemplate.update(UPDATE_USER_VALIDITY_STATUS, new Object[] { validity, email });
  }

  @Override
  public Boolean isValidUser(String email) throws OTPResourceNotFoundException {
    String validity = jdbcTemplate.queryForObject(CHECK_VALIDITY_STATUS, new Object[] { email }, String.class);
    if(validity.equalsIgnoreCase("Y")){
      return true;
    }
    return false;
  }

}