package com.scm.scm20.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.scm20.entities.Contact;
import com.scm.scm20.entities.Social;
import com.scm.scm20.entities.SocialLinks;
import com.scm.scm20.entities.User;
import com.scm.scm20.forms.ContactForm;
import com.scm.scm20.forms.SocialProfileForm;
import com.scm.scm20.helper.Helper;
import com.scm.scm20.helper.Message;
import com.scm.scm20.helper.MessageType;
import com.scm.scm20.services.ContactService;
import com.scm.scm20.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/contact")
@RequiredArgsConstructor
public class ContactController {

    private final ModelMapper modelMapper;

    private final UserService userService;

    private final ContactService contactService;

    private Logger logger = LoggerFactory.getLogger(ContactController.class);

    @GetMapping("/addContact")
    public String addContactView(Model model){

        ContactForm contactForm = new ContactForm();

        // contactForm.setName("Aman Bhardwaj");
        // contactForm.setFavourite(true);
        model.addAttribute("contactForm", contactForm);
        model.addAttribute("platformValues", Arrays.asList(Social.values()));
        return "user/addContact";
    }

    @PostMapping("/process_contact")
    public String process_contact(@Valid @ModelAttribute ContactForm contactform ,BindingResult rBindingResult, Authentication authentication , HttpSession httpSession){

        System.out.println(contactform);

        if(rBindingResult.hasErrors()){

            logger.info("Contact Form has errors : {}",rBindingResult.hasErrors());
            return "user/addContact";
        }

        String username = Helper.getUsernameOfLoggedInUser(authentication);

        Optional<User> user = userService.getuserByEmailId(username);

        Contact contact = modelMapper.map(contactform, Contact.class);

        contact.setUser(user.orElse(null));

        List<SocialLinks> socialLinks = new ArrayList<>();

        for(SocialProfileForm link : contactform.getSocialLinks()){

            SocialLinks socialLink = modelMapper.map(link , SocialLinks.class);
            logger.info("Social link is : {}",socialLink);
            socialLinks.add(socialLink);

        }

        contact.setSocialLinks(socialLinks);

        // Save the data in DB

        contactService.saveContact(contact);

        logger.info("Contact was saved successfully", contact.getId());

        Message msg = new Message();
        msg.setType(MessageType.green);
        msg.setContent("Contact Registered successfully");

        httpSession.setAttribute("message", msg);

        return "redirect:/user/addContact";

    }

}
