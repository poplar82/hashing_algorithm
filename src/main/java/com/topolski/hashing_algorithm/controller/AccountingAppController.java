package com.topolski.hashing_algorithm.controller;

import com.topolski.hashing_algorithm.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class AccountingAppController {
    private final UserService userService;

    @Autowired
    public AccountingAppController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/master")
    public String prepareMasterPage(Principal principal) {
        return "Master page. You are logged in as: " + principal.getName();
    }

    @GetMapping("/admin")
    public String prepareAdminPage(Principal principal) {
        return "Admin page. You are logged in as: " + principal.getName();
    }

    @GetMapping("/user")
    public String prepareUserPage(Principal principal) {
        return "User page. You are logged in as: " + principal.getName();
    }

    @GetMapping("/register")
    public String prepareRegistrationPage() {
        return "<div id=\"panel\">"
                + "<form action=\"/api/register\" method=\"POST\">"
                + "<label for=\"username\">Username:</label>"
                + "<input type=\"text\" id=\"username\" name=\"username\"><br>"
                + "<label for=\"password\">Password:</label>"
                + "<input type=\"password\" id=\"password\" name=\"password\"><br>"
                + "<label for=\"firstname\">Name:</label>"
                + "<input type=\"text\" id=\"firstname\" name=\"firstname\"><br>"
                + "<label for=\"lastname\">Last name:</label>"
                + "<input type=\"text\" id=\"lastname\" name=\"lastname\"><br>"
                + "<label for=\"email\">E-mail:</label>"
                + "<input type=\"text\" id=\"email\" name=\"email\"><br>"
                + "<label for=\"tel\">Tel:</label>"
                + "<input type=\"text\" id=\"tel\" name=\"tel\"><br>"
                + "<label for=\"admin\">Do you want to become an admin:</label>"
                + "<input type=\"checkbox\" id=\"admin\" name=\"admin\">"
                + "<input type=\"submit\" value=\"Register\">"
                + "</form>"
                + "</div>";
    }

    @PostMapping("/register")
    public String processRegistrationData(
            @RequestParam final String username,
            @RequestParam final String password,
            @RequestParam(name = "firstname") final String firstName,
            @RequestParam(name = "lastname") final String lastName,
            @RequestParam final String email,
            @RequestParam final String tel,
            @RequestParam(required = false) final boolean admin) {
        if (userService.checkUserIfUsernameExists(username)) {
            return "User with same username already exists";
        }
        if (userService.checkUserIfEmailExists(email)) {
            return "User with same email already exists";
        }
        if (password.length() < 5) {
            return "registration false";
        }
        try {
            userService.addNewUser(username, password, firstName, lastName, tel, email, admin);
        } catch (Exception e) {
            return "registration false";
        }
        return "registration completed successfully.";
    }
}
