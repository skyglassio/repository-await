package com.example.demo.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
public class User {
  @Id
  private String id;
  private String username;
  private List<String> roleIds;
  private String roleName;

  public static User getInstance() {
    return new User();
  }

}
