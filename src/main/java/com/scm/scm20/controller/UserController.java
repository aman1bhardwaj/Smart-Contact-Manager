package com.scm.scm20.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    // user dashboard

    @GetMapping("/dashboard")
    public String getdashboard() {
        return "user/dashboard";
    }

    //User Profile package
    @GetMapping("/profile")
    public String getProfile() {
        return "user/profile";
    }

    // user add contacts page

    // edit contact

    // search contact

    // delete contact

    // view all contact

}
