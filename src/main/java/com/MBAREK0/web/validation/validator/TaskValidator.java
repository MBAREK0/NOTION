package com.MBAREK0.web.validation.validator;

import com.MBAREK0.web.entity.Task;

public class TaskValidator {

    public static boolean isValidTask(Task task) {
        if (task == null) {
            return false;
        }
        return  Validator.isValidName(task.getTitle()) &&
                Validator.isValidDescription(task.getDescription()) &&
                Validator.isValidStatus(task.getStatus().toString()) &&
                Validator.isValidPeriod(task.getStartDate(),task.getEndDate()) &&
                Validator.isNotNull(task.getUser()) &&
                Validator.isNotNull(task.getUser().getId()) &&
                Validator.isNotNull(task.getManager()) &&
                Validator.isNotNull(task.getManager().getId());

    }
}
