package com.scm.scm20.controller;

import java.security.Principal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);
    // user dashboard

    @RequestMapping(value = "/dashboard", method = { RequestMethod.GET, RequestMethod.POST })
    public String getdashboard(Model model, Authentication authentication) {

        // both GET and POST = has been set for this method as while doing form login
        // post mapping is expected and
        // while doing oauth login get mapping is expected. thats why both method are
        // configured.

        return "user/dashboard";
    }

    // User Profile page
    @GetMapping("/profile")
    public String getProfile(Principal principal) {

        logger.info("Logined with user principal : {}", principal.getName());
        return "user/profile";
    }

    // user add contacts page

    // edit contact

    // search contact

    // delete contact

    // view all contact

}
