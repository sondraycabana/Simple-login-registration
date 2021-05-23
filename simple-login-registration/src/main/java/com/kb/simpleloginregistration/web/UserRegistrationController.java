package com.kb.simpleloginregistration.web;
import com.kb.simpleloginregistration.data.model.User;
import com.kb.simpleloginregistration.service.UserService;
import com.kb.simpleloginregistration.service.exceptions.RegisterException;
import com.kb.simpleloginregistration.web.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

    @Autowired
    private UserService userService;
    public UserRegistrationController(UserService userService){
        super();
        this.userService = userService;
    }

    //this method is used to return object of registrationDto
    //we are using this user in the thymeleaf form "${user}"
    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto(){
        return new UserRegistrationDto();
    }

    // this one or the one above, one of this is fine
    @GetMapping
    public String showRegistrationForm(){
        return "registration";
    }

    @PostMapping("/register")
    public String registerUserAccount(@ModelAttribute("user") @Valid
                                                  UserRegistrationDto userRegistrationDto, BindingResult result) throws RegisterException {
      userService.register(userRegistrationDto);

        if(result.hasErrors()){
            return "redirect:/registration?error";
        }
        return "redirect:/registration?success";
    }
}





