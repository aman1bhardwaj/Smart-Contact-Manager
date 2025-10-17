package com.scm.scm20.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.scm.scm20.entities.User;
import com.scm.scm20.helper.Helper;
import com.scm.scm20.services.UserService;


@ControllerAdvice
public class RootController {

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(RootController.class);

    @ModelAttribute
    public void addUserToAllRoute(Model model, Authentication authentication) {

        if(authentication == null) return ;

        String username = Helper.getDetailsAndEmailofLoggedInUser(authentication);

        logger.info("add users to all route  {}" ,username );

        Optional<User> user = userService.getuserByEmailId(username);

        if(user.isPresent()){
        model.addAttribute("loggedInUser", user.get());
        }
        else{
            model.addAttribute("loggedInUser", null);
        }

        logger.info("Logined with auhtentication Prnicnpal USer : {}", user.get().getName());

    }

}
