package com.scm.scm20.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Userform {

    @NotBlank(message="Username is required")
    @Size(min = 3 , message="Min 3 characters is required")
    private String name;

    @NotBlank(message="Email is required")
    @Email(message = "Valid Email is required")
    private String email;

    @NotBlank(message ="password is required")
    @Size(min=3 , message ="Min 8 characters are required")
    private String password;

    @NotBlank(message="About is required")
    private String about;

    @Size(min=8 , max=12 , message = "Invalid Phone Number")
    private String phoneNumber;


}
