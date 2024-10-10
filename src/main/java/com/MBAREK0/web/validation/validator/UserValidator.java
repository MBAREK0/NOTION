package com.MBAREK0.web.validation.validator;

import com.MBAREK0.web.entity.User;

public class UserValidator {

    public static boolean isValidUser(User user){
        if (user == null) {
            return false;
        }
        if (user.getRole() == null) {
            return false;
        }
        return Validator.isValidEmail(user.getEmail())&&
                Validator.isValidPassword(user.getPassword())&&
                Validator.isValidName(user.getFirstName()) &&
                Validator.isValidName(user.getLastName()) &&
                Validator.isValidName(user.getUsername()) &&
                isValidRole(user.getRole().toString());
    }

    public static boolean isValidRole(String role) {
        return role.equals("user") || role.equals("manager");
    }



}
