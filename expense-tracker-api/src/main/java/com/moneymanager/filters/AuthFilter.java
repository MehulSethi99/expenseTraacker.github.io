package com.moneymanager.filters;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.moneymanager.Constants;
import java.io.IOException;


public class AuthFilter extends GenericFilterBean {
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;

    String authHeader = httpRequest.getHeader("Authorization");
    if (authHeader != null) {
      String[] authHeaderArr = authHeader.split("Bearer ");
      if (authHeaderArr.length > 1 && authHeaderArr[1] != null) {
        String token = authHeaderArr[1];

        try {
          Claims claims = Jwts.parser().setSigningKey(Constants.API_SECRET_KEY).parseClaimsJws(token).getBody();
          httpRequest.setAttribute("email", claims.get("email").toString());
        } catch (Exception e) {
          httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "invalid/expired token");
          return;
        }
      } else {
        httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "Authorization token must be Bearer [token]");
        return;
      }
    } else {
      httpResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "Authorization token must be provided");
      return;
    }
    chain.doFilter(httpRequest, httpResponse);
  }
}