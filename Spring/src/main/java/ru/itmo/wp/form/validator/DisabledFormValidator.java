package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.form.DisabledForm;
import ru.itmo.wp.service.UserService;

@Component
public class DisabledFormValidator implements Validator {
    private final UserService userService;

    public DisabledFormValidator(UserService userService) {
        this.userService = userService;
    }

    public boolean supports(Class<?> clazz) {
        return DisabledForm.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            DisabledForm disabledForm = (DisabledForm) target;
            if (userService.findById(disabledForm.getUserId()) == null) {
                errors.rejectValue("button", "button.invalid-user-id", "invalid user id");
            }
        }
    }
}
