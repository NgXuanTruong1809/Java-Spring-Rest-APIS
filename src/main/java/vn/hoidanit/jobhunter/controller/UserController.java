package vn.hoidanit.jobhunter.controller;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    public String createNewUser() {
        return "create user";
    }
}
