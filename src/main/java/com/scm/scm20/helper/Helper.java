package com.scm.scm20.helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class Helper {

    public static String getDetailsAndEmailofLoggedInUser(Authentication authentication) {

        // now we need to differnetiate the logged in user is from google or github or
        // normal login using email id and password

        // To differnetiate , we know logged in with google ,github or any social login
        // will be done via Oauth2

        // Currently we are returning the whole user from the DB

        if (authentication instanceof OAuth2AuthenticationToken) {

            // in this part of code , it means logged in with Oauth2

            OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;

            var authorizedClientRegistrationId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

            OAuth2User defUser = (OAuth2User) authentication.getPrincipal();

            String username="";

            // System.out.println("prin " + principal);

            if (authorizedClientRegistrationId.equalsIgnoreCase(AppConstants.GOOGLE)) {

                username = defUser.getAttribute("email").toString();

                System.out.println("Data FROM GOOGLE" + defUser.getName());

                System.out.println("Data " + oAuth2AuthenticationToken);

            } else if (authorizedClientRegistrationId.equalsIgnoreCase(AppConstants.GIT_HUB)) {

                username = defUser.getAttribute("email") != null ? defUser.getAttribute("email")
                    : defUser.getAttribute("login").toString() + "@gmail.com";
                System.out.println("DATA FROM GITHUB" + defUser.getName());
            }

            return username;

        } else {
            System.out.println("me " + authentication.getName());
            
        }

        return authentication.getName();
    }
}
