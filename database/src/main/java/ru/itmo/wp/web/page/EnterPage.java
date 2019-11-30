package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;

/**
 * @noinspection unused
 */
public class EnterPage extends Page {

    private void enter(HttpServletRequest request) throws ValidationException {
        String loginOrEmail = request.getParameter("loginOrEmail");
        String password = request.getParameter("password");

        User user = getUserService().validateAndFindByLoginOrEmailAndPassword(loginOrEmail, password);
        setUser(user);
        setMessage("Hello, " + user.getLogin());

        getEventService().insertEnter(user);

        throw new RedirectException("/index");
    }
}
