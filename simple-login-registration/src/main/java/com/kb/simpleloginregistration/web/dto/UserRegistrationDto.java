package com.kb.simpleloginregistration.web.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.*;
@Data
public class UserRegistrationDto {

    private String firstName;
    private String lastName;

    @Email
    private String email;

    private String password;

//    // this constructor is connected to  @ModelAttribute("user") in userRegistrationDto
//     public UserRegistrationDto(){
//
//    }



}
