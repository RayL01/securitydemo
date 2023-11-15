package com.eay.securitydemo.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: shadyyyyyl
 * @Date: 2023/11/15/00:23
 * @Description:
 */
@RestController
@RequestMapping("/api/v1/demo-controller")
public class DemoController {
  @GetMapping
  public ResponseEntity<String> sayHello(){
    return ResponseEntity.ok("hello from secured endpoint");
  }
}
