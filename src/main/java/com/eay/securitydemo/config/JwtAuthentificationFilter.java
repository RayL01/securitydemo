package com.eay.securitydemo.config;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: shadyyyyyl
 * @Date: 2023/11/11/22:57
 * @Description:
 */
@Component
@RequiredArgsConstructor
public class JwtAuthentificationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  @Override
  protected void doFilterInternal(
          @Nonnull HttpServletRequest request,
          @Nonnull HttpServletResponse response,
          @Nonnull FilterChain filterChain
  ) throws ServletException, IOException {
    //TODO
    final String authHeader = request.getHeader("Authorization");
    final String jwt;
    final String userEmail;
    if(authHeader == null || !authHeader.startsWith("Bearer ")){
      filterChain.doFilter(request, response);
      return;
    }
    jwt = authHeader.substring(7);
    userEmail = jwtService.extractUsername(jwt);//todo: extract the exact userEmail from jwt token

  }
}
