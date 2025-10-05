package com.scm.scm20.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/home")
    public String getHome(Model model) {

        // sending data to view like Home.html
        System.out.println("Home Page");
        model.addAttribute("name", "Aman Bhardwaj");
        model.addAttribute("id", "1234");
        return new String("home");
    }

    // about mapping

    @GetMapping("/about")
    public String aboutPage(Model model) {
        model.addAttribute("isLogin", false);
        System.out.println("About Page");
        return new String("about");
    }

    // services mapping

    @GetMapping("/services")
    public String servicesPage() {
        System.out.println("Services Page");
        return new String("services");
    }

    // Contact Mapping

    @GetMapping("/contact")
    public String getContact(){
        System.out.println("Contact Page");
        return new String("contact");
    }

    //Login Mapping

    @GetMapping("/login")
    public String doLogin(){
        System.out.println("Login Page");
        return new String("login");
    }

    //register mapping

    @GetMapping("/register")
    public String doRegister(){
        System.out.println("Register Page");
        return new String("register");
    }

}
