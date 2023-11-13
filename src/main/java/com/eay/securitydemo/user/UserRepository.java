package com.eay.securitydemo.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: shadyyyyyl
 * @Date: 2023/11/12/23:40
 * @Description:
 */

/**
 * since it extends JpaRep, so no need for @repository here, spring data aunomatically do that for you
 *
 */
public interface UserRepository extends JpaRepository<User, Integer> {
  Optional<User> findByEmail(String email);
}
