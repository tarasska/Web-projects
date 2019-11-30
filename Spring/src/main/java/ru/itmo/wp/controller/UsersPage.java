package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.form.DisabledForm;
import ru.itmo.wp.form.validator.DisabledFormValidator;
import ru.itmo.wp.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@Controller
public class UsersPage extends Page {
    private final UserService userService;
    private final DisabledFormValidator disabledFormValidator;

    public UsersPage(UserService userService, DisabledFormValidator disabledFormValidator) {
        this.userService = userService;
        this.disabledFormValidator = disabledFormValidator;
    }

    @InitBinder("disabledForm")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(disabledFormValidator);
    }

    @GetMapping("/users/all")
    public String users(Model model) {
        model.addAttribute("users", userService.findAll());
        return "UsersPage";
    }

    @PostMapping("/users/setDisabled")
    public String setDisabled(@Valid @ModelAttribute("disabledForm") DisabledForm disabledForm,
                                 BindingResult bindingResult, HttpSession httpSession) {
        User user = getUser(httpSession);
        if (!bindingResult.hasErrors() && user != null) {
            userService.updateDisabled(disabledForm.getUserId(), disabledForm.getDisabled());
            if (user.getId() == disabledForm.getUserId()) {
                return "redirect:/logout";
            }
        }
        return "redirect:/users/all";
    }
}
