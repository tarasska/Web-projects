package ru.itmo.wp.web.page;


import java.util.Map;

/**
 * @noinspection unused
 */
public class UsersPage extends Page {
    protected void action(Map<String, Object> view) {
        super.action(view);

        view.put("users", getUserService().findAll());
    }
}
