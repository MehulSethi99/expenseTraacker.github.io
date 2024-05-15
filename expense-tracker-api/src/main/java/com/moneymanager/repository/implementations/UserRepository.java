package com.moneymanager.repository.implementations;

import java.sql.PreparedStatement;
import java.sql.Statement;

import com.moneymanager.exceptions.UserAuthException;
import com.moneymanager.model.User;
import com.moneymanager.repository.UserRepositoryInterface;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository implements UserRepositoryInterface {

  private static final String SQL_CREATE = "INSERT INTO exp_tracker_users( first_name, last_name, avatar_url, email, password, prefered_currency ) VALUES(?, ?, ?, ?, ?, ?)";
  private static final String SQL_SUM_BY_EMAIL = "select sum(amount) from exp_tracker_users WHERE EMAIL = ? and year=? and month =?";
  private static final String SQL_COUNT_BY_EMAIL = "SELECT COUNT(*) FROM exp_tracker_users WHERE EMAIL = ?";
  private static final String SQL_FIND_BY_ID = "SELECT first_name, last_name, avatar_url, email, password, prefered_currency, googleauthkey " +
  "FROM exp_tracker_users WHERE email = ?";
  private static final String SQL_FIND_BY_EMAIL = "SELECT first_name, last_name, avatar_url, email, password, prefered_currency, googleauthkey " +
  "FROM exp_tracker_users WHERE EMAIL = ?";

  @Autowired
  JdbcTemplate jdbcTemplate;

  private RowMapper<User> userRowMapper = ((rs, rowNum) -> {
    return new User(
            rs.getString("email"),
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getString("password"),
            rs.getString("avatar_url"),
            rs.getString("prefered_currency"),
            rs.getString("googleauthkey"));
});

  @Override
  public String create(String firstName, String lastName, String email, String avatarUrl, String password,
  String preferedCurrency) throws UserAuthException {
    String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
    try {
      KeyHolder keyHolder = new GeneratedKeyHolder();
      jdbcTemplate.update(connection->{
        PreparedStatement ps = connection.prepareStatement(SQL_CREATE,  Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, firstName);
        ps.setString(2, lastName);
        ps.setString(3, avatarUrl);
        ps.setString(4, email);
        ps.setString(5, hashedPassword);
        ps.setString(6, preferedCurrency);
        return ps;
      }, keyHolder);
      return (String) keyHolder.getKeys().get("email");
    } catch (Exception e) {
      throw new UserAuthException("Could not create user account");
    }
  }

  @Override
  public User findEmailAndPassword(String email, String password) throws UserAuthException {
    try {
      User user = jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL, new Object[]{email}, userRowMapper);
      if(!BCrypt.checkpw(password.toString(), user.getPassword()))
          throw new UserAuthException("Invalid email/password");
      return user;
  }catch (EmptyResultDataAccessException e) {
      throw new UserAuthException("Invalid email/password");
  }
  }

  @Override
  public Integer getCounterByEmail(String email) {
    return jdbcTemplate.queryForObject(SQL_COUNT_BY_EMAIL, new Object[]{email}, Integer.class);
  }

  @Override
  public User findById(String email) {
    return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{email}, userRowMapper);
  }
  
}