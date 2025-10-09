package com.scm.scm20.helper;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import jakarta.servlet.http.HttpSession;

@Component
public class SessionHelper {

    // Below method is used in message.html file to remove the message once
    // displayed on the signUp form
    public static void removeMessage() {

        try {
            System.out.println("Removing message from Session Helper");
            HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
                    .getSession();
            session.removeAttribute("message");
        } catch (Exception e) {
            System.out.println("Error in removing the session " + e);
            e.printStackTrace();
        }
    }

}
