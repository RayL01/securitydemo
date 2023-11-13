package com.eay.securitydemo.config;

import com.eay.securitydemo.user.UserRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import lombok.RequiredArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: shadyyyyyl
 * @Date: 2023/11/12/23:31
 * @Description:
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {
  private final UserRepository repository;
  @Bean
  public UserDetailsService userDetailsService(){
    return username -> repository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }
}
