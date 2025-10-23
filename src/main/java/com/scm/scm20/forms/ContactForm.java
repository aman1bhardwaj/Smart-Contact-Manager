package com.scm.scm20.forms;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.scm.scm20.helper.Validations.SocialLinkValid;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ContactForm {


    @NotBlank(message = "Name should be entered")
    @Size(min=3 , message="Min 3 characters is required")
    private String name;

    @NotBlank(message = "Email should be entered")
    @Email(message ="Enter Valid email address")
    private String email;

    @NotBlank(message="Phone is required")
    @Size(min=5,max=10,message="Enter Valid Phone Number")
    private String phoneNumber;

    @NotBlank(message="Description should be entered")
    private String description;

    private AddressForm addressform;
    
    private boolean favourite;

    private MultipartFile profileImage;


    @Valid
    @SocialLinkValid
    @Size(max=5 , message="Maximaum 5 social profile links can be added")
    private List<SocialProfileForm> socialLinks = new ArrayList<>();

}
