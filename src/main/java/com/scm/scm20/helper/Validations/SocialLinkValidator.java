package com.scm.scm20.helper.Validations;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.scm.scm20.forms.SocialProfileForm;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SocialLinkValidator implements ConstraintValidator<SocialLinkValid, List<SocialProfileForm>> {

    @Override
    public boolean isValid(List<SocialProfileForm> socialProfiles, ConstraintValidatorContext context) {

        if (socialProfiles == null || socialProfiles.isEmpty()) {

            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Atleast one social Profile Link is requried");

            return false;
        }

        Set<String> platform = new HashSet<>();
boolean isValid = true;
        if (socialProfiles != null) {
            
            for (SocialProfileForm profileLink : socialProfiles) {

                if (profileLink == null) {
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate("Platform and Link field is requried");

                    isValid = false;
                }

                if (!platform.add(profileLink.getPlatform())) {

                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate("Duplicate platform: " + profileLink.getPlatform())
                        .addPropertyNode("profileLink.platform");
                    isValid = false;
                }

                if(profileLink.getLink()== null || !profileLink.getLink().startsWith("http")){

                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate("Invalid URL " + profileLink.getLink())
                        .addPropertyNode("profileLink.link");
                    isValid = false;
                }
            }
        }

        return isValid;
    }

}
