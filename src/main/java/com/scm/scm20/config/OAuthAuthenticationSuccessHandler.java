package com.scm.scm20.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.scm20.entities.Providers;
import com.scm.scm20.entities.User;
import com.scm.scm20.helper.AppConstants;
import com.scm.scm20.repositories.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepo userRepo;

    Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        logger.info("Sucess Handled OAuthAuthenticationSuccessHandler");

        /*
         * ---- AS we know , every social login will have its client ID and client
         * secret key and they will also expose different attributes to the client. Now
         * it is possible that the attributes which google is sending to the client and
         * the attributes set by Github to the client are very different , so saving
         * those attributes will aslo be differnet for each social login. TO resiove the
         * issue , we will use the authentication and typecast to
         * Oauth2AuhtenticationToken
         */

        logger.info("Finding the Provider");

        var oAuthAuthentication = (OAuth2AuthenticationToken) authentication;

        String authorizedClientRegistrationId = oAuthAuthentication.getAuthorizedClientRegistrationId();

        logger.info(authorizedClientRegistrationId);

        DefaultOAuth2User oAuthUser = (DefaultOAuth2User) authentication.getPrincipal();

        oAuthUser.getAttributes().forEach((key, value) -> {
            logger.info("{} => {}", key, value);
        });

        User user = new User();

        String email = "";

        user.setUserId(UUID.randomUUID().toString());
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        user.setEmailVerified(true);
        user.setEnabled(true);
        user.setAbout("Login done with " + authorizedClientRegistrationId);
        if (authorizedClientRegistrationId.equalsIgnoreCase("google")) {

            user.setEmail(oAuthUser.getAttribute("email"));
            user.setName(oAuthUser.getAttribute("name"));
            user.setProfilePic(oAuthUser.getAttribute("picture"));
            user.setPassword("Google password");
            user.setProvider(Providers.GOOGLE);
            user.setProviderId(oAuthUser.getAttribute("sub"));

        } else if (authorizedClientRegistrationId.equalsIgnoreCase("github")) {

            email = oAuthUser.getAttribute("email") != null ? oAuthUser.getAttribute("email")
                    : oAuthUser.getAttribute("login");
            user.setEmail(email);
            user.setName(oAuthUser.getAttribute("login"));
            user.setProfilePic(oAuthUser.getAttribute("picture"));
            user.setPassword("Git Hub password");
            user.setProvider(Providers.GITHUB);
            user.setProviderId(oAuthUser.getName());

        }

        if (userRepo.findByEmail(email).orElse(null) == null) {
            userRepo.save(user);
            logger.info("Details Saved in DB Successfully for" + email);
        }

        logger.info("Redirecting");

        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/dashboard");
    }

}
