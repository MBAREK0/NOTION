package com.MBAREK0.web.validation.validator;

import com.MBAREK0.web.util.DateUtil;

import java.time.LocalDate;

public class Validator {
    public static boolean isValidEmail(String email) {
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    }

    public static boolean isValidPassword(String password) {
        return password.length() >= 8;
    }

    public static boolean isValidName(String name) {
        return name.matches("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$");
    }

    public static <T> boolean isNotNull(T t) {
        return t != null;
    }

    public static boolean isValidDescription(String description) {
        return description.length() <= 2500 && description.length() > 0;
    }

    public static boolean isValidStatus(String status) {
        return status.equals("pending") || status.equals("in_progress") || status.equals("completed") || status.equals("overdue");
    }

    public static boolean isValidPeriod(LocalDate startDate, LocalDate endDate) {
        return DateUtil.isValidPeriod(startDate, endDate);
    }

}
