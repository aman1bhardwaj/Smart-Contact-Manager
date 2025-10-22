package com.scm.scm20.forms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddressForm {

    @NotBlank(message="Street should be entered")
    private String street;

    @NotBlank(message="City should be entered")
    private String city;

    @NotBlank(message="State should be entered")
    private String state;

    @NotBlank(message="ZipCode should be entered")
    @Size(min=6,max=6,message="6 digits are allowed")
    private String zipcode;

    @NotBlank(message="Country should be entered")
    private String country;

}
