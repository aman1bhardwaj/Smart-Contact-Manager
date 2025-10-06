package com.scm.scm20.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.scm.scm20.entities.User;
import com.scm.scm20.forms.Userform;
import com.scm.scm20.services.UserService;

@Controller
public class PageController {

        @Autowired
        private UserService userService;

        @Value("${user.default.profile-pic}")
        private String profile_pic;

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
    public String doRegister(Model model){
        System.out.println("Register Page");

        Userform userform = new Userform();
        model.addAttribute("userForm", userform);

        //we can send default value as well
        return new String("register");
    }

    @PostMapping("/do-register")
    public String doRegisterContact(@ModelAttribute Userform userform){

        //data form se yahan lana padega
        //validate karna padega forma ka data
        //save karna padega data in DB
        User user = User.builder()
                .name(userform.getName())
                .email(userform.getEmail())
                .password(userform.getPassword())
                .phoneNumber(userform.getPhoneNumber())
                .about(userform.getAbout())
                .profilePic(profile_pic) // default profile pic set kar di
                .build();


        userService.saveUser(user);
        //exception hanlding karni padegi
        //redirect karna padega success ya error message ke basis pe
        System.out.println("Registered Successfully");
        return "redirect:/register";
    }

}
