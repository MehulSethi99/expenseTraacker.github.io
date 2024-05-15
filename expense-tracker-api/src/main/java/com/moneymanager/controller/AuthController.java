package com.moneymanager.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

import com.moneymanager.Constants;
import com.moneymanager.model.OTP;
import com.moneymanager.model.User;
import com.moneymanager.service.AuthServiceInterface;
import com.moneymanager.service.EmailServiceInterface;
import com.moneymanager.service.GoogleOtpServiceInterface;
import com.moneymanager.service.OTPServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/users")
public class AuthController {

  @Autowired
  AuthServiceInterface authService;

  @Autowired
  EmailServiceInterface emailService;

  @Autowired
  OTPServiceInterface otpService;

  @Autowired
  JavaMailSender sender;

  @Autowired
  GoogleOtpServiceInterface googleOtpService;

  // @Route POST /api/users/login
  // @Desc Login user
  // @Access Public

  @PostMapping("/login")
  public ResponseEntity<Map<String, String>> loginUser(HttpServletRequest request,
      @RequestBody Map<String, Object> userMap) {

    String email = (String) userMap.get("email");
    String password = (String) userMap.get("password");
    try {
      if (otpService.isValidUser(email)) {
        User user = authService.validateUser(email, password);
        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
      } else {
        Map<String, String> invlaidUser = new HashMap<>();
        invlaidUser.put("invalid", "Account not verified");
        return new ResponseEntity<>(invlaidUser, HttpStatus.OK);
      }
    } catch (Exception e) {
      Map<String, String> invlaidUser = new HashMap<>();
      invlaidUser.put("invalid", e.toString());
      return new ResponseEntity<>(invlaidUser, HttpStatus.OK);
    }
  }


  @PostMapping("/google-auth-login")
  public ResponseEntity<Map<String, String>> qrauth(HttpServletRequest request,
      @RequestBody Map<String, Object> userOtp) {

    String status = "";
    String code = String.valueOf(userOtp.get("password"));
    String email = String.valueOf(userOtp.get("email"));
    Map<String, String> response = new HashMap<>();

    User user = authService.findById(email);

    if (code.equals(googleOtpService.getTOTPCode(user.getGoogleauthkey()))) {
      System.out.println("Logged in successfully");
      status = "Logged in successfully";
      return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    } else {
      System.out.println("Invalid 2FA Code");
      status = "Invalid 2FA Code";
      return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

  }

  

  // @Route POST /api/users/register
  // @Desc Register new user
  // @Access Public

  @PostMapping("/register")
  public ResponseEntity<Map<String, String>> registerUser(HttpServletRequest request,
      @RequestBody Map<String, Object> userMap) {
    String firstName = (String) userMap.get("firstName");
    String lastName = (String) userMap.get("lastName");
    String email = (String) userMap.get("email");
    String avatarUrl = (String) userMap.get("avatarUrl");
    String password = (String) userMap.get("password");
    String preferedCurrency = (String) userMap.get("preferedCurrency");
    User user = authService.registerUser(firstName, lastName, email, avatarUrl, password, preferedCurrency);
    try {
      otpService.createOTP(user.getEmail());
      OTP otp = otpService.getOTP(user.getEmail());
      String host = request.getHeader("host");
      String userOTPlink = "http://" + host + "/api/users/otpvalidation/" + otp.getOtp() + "/" + otp.getEmail();
      //sendEmail(userOTPlink, "Registered successfully", user.getEmail());
      String htmlMessage ="<h1>Click the below lick to successfully register your account </h1>" + "<a href=" + userOTPlink
      + ">" + userOTPlink + "</a>";
      emailService.sendMail(user.getEmail(), "Registered successfully", htmlMessage);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
  }

  // @Route GET /api/users/otpvalidation/{otp}/{email}
  // @Desc Validate user OTP
  // @Access Public

  @GetMapping("/otpvalidation/{otp}/{email}")
  public ResponseEntity<String> validateOTP(@PathVariable("otp") String otp, @PathVariable("email") String email) {
    OTP userOtp = otpService.getOTP(email);
    String validationResponse;
    try {
      if ((Long.parseLong(userOtp.getExpiryTime().toString()) > (Long) System.currentTimeMillis() / 1000)
          && userOtp.getOtp().equals(otp)) {
        otpService.updateUserValidity(email, "Y");
        validationResponse = "Email Vaidation success now login with your email ID and password";
      } else {
        otpService.updateUserValidity(email, "N");
        validationResponse = "User Invalid";
      }
      otpService.deleteOTP(email);
    } catch (Exception e) {
      validationResponse = "Could not validate Email, You should have cicked the email link after 240 Seconds or the email link could be corrupted";
    }
    return new ResponseEntity<>(validationResponse, HttpStatus.OK);
  }

  private Map<String, String> generateJWTToken(User user) {
    long timestamp = System.currentTimeMillis();

    String token = Jwts.builder()
        .signWith(SignatureAlgorithm.HS512, DatatypeConverter.parseBase64Binary(Constants.API_SECRET_KEY))
        .setIssuedAt(new Date(timestamp)).setExpiration(new Date(timestamp + Constants.TOKEN_VALIDITY))
        .claim("email", user.getEmail()).claim("firstName", user.getFirstName()).claim("lastName", user.getLastName())
        .claim("avatarUrl", user.getAvatarUrl()).claim("preferedCurrency", user.getPreferedCurrency()).compact();
    Map<String, String> map = new HashMap<>();
    map.put("token", token);
    return map;
  }

}