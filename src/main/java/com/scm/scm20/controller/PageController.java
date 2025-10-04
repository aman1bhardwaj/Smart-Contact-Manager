package com.scm.scm20.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/page")
    public String getHome(Model model) {

        // sending data to view like Home.html
        System.out.println("Hello World");
        model.addAttribute("name", "Aman Bhardwaj");
        model.addAttribute("id", "1234");
        return "home";
    }

    // about mapping

    @GetMapping("/about")
    public String aboutPage(Model model) {
        model.addAttribute("isLogin", false);
        System.out.println("About Page");
        return "about";
    }

    // services mapping

    @GetMapping("/services")
    public String servicesPage() {
        System.out.println("Services Page");
        return "services";
    }

}
