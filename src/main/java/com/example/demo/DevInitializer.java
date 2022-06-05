package com.example.demo;

import com.example.demo.role.Role;
import com.example.demo.role.RoleRepository;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Component
public class DevInitializer implements ApplicationListener<ApplicationReadyEvent> {
  @Autowired
  RoleRepository roleRepository;
  @Autowired
  UserRepository userRepository;

  @Override
  public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
    roleRepository.deleteAll().block();
    userRepository.deleteAll().block();

    Role adminRole = Role.getInstance();
    adminRole.setName("ADMIN");
    roleRepository.save(adminRole).block();

    Role userRole = Role.getInstance();
    userRole.setName("USER");
    roleRepository.save(userRole).block();

    User regularUser = User.getInstance();
    regularUser.setUsername("regularUser");
    regularUser.setRoleIds(Arrays.asList(userRole.getId()));
    userRepository.save(regularUser).block();

    Mono<User> userMono = userRepository.findByUsername(regularUser.getUsername())
//            .thenReturn(User.builder()
//                    .username(u.getUsername())
//                    .userStatus(userService.getRoles(u).get(0)));
//            .doOnSuccess(u ->
//                    User.builder()
//                            .username(u.getUsername())
//                            .userStatus(userService.getRoles(u).get(0))
//                            .build());
//            .log()
            .map(u -> {
                      User user = new User();
                      user.setUsername(u.getUsername());
                      user.setRoleName(roleRepository.findById(u.getRoleIds().get(0)).block().getName());
                      return user;
            });

    User user = userMono.block();
    System.out.println("user.getRoleName() = " + user.getRoleName());
  }

}
