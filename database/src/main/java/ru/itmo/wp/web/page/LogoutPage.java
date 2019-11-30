package ru.itmo.wp.web.page;

import ru.itmo.wp.web.exception.RedirectException;

import java.util.Map;

/**
 * @noinspection unused
 */
public class LogoutPage extends Page {

    @Override
    protected void action(Map<String, Object> view) {
        super.action(view);

        getEventService().insertLogout(getUser());

        getRequest().getSession().removeAttribute("user");

        setMessage("Good bye. Hope to see you soon!");

        throw new RedirectException("/index");
    }
}
