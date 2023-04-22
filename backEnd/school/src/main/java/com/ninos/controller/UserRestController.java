package com.ninos.controller;

import com.ninos.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class UserRestController {

    private final UserService userService;

    @GetMapping("/users")
    public boolean checkIfEmailExists(@RequestParam(name = "email", defaultValue = "") String email){
        return userService.loadUserByEmail(email) != null;
    }

}
