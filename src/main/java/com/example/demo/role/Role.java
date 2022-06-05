package com.example.demo.role;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
@Getter
@Setter
public class Role {
  @Id
  private String id;
  private String name;

  public static Role getInstance() {
    return new Role();
  }
}
