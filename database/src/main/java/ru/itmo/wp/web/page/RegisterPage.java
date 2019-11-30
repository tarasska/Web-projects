package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;

/**
 * @noinspection unused
 */
public class RegisterPage extends Page {

    private void register(HttpServletRequest request) throws ValidationException {
        User user = new User();
        user.setLogin(request.getParameter("login"));
        user.setEmail(request.getParameter("email"));
        String password = request.getParameter("password");
        String passwordConfirmation = request.getParameter("passwordConfirmation");

        getUserService().validateRegistration(user, password, passwordConfirmation);
        getUserService().register(user, password);

        setUser(user);
        setMessage("You are successfully registered!");

        throw new RedirectException("/index");
    }
}
