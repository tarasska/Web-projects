package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @noinspection unused
 */
public class UsersPage extends Page {
    private final UserService userService = new UserService();

    protected void action(HttpServletRequest request, Map<String, Object> view) {
        super.action(request, view);
    }

    private void findAll(HttpServletRequest request, Map<String, Object> view) {
        view.put("users", userService.findAll());
    }

    private void findUser(HttpServletRequest request, Map<String, Object> view) {
        view.put("user",
                userService.find(Long.parseLong(request.getParameter("userId"))));
    }

    private void changeAdminStatus(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        long userId;
        try {
            userId = Long.parseLong(request.getParameter("userId"));
        } catch (NumberFormatException e) {
            throw new ValidationException("Something went wrong");
        }

        User targetUser = userService.find(userId);

        boolean newAdmin = !targetUser.isAdmin();

        User user = getUser(request);

        if (user == null || !user.isAdmin()) {
            setMessage(request, "Log in admin account to change your admin status");
            throw new RedirectException("/index");
        }

        userService.setAdmin(userId, newAdmin);

        view.put("newAdminStatus", newAdmin ? "disable" : "enable");
        view.put("isAdmin", newAdmin);

        if (userId == user.getId()) {
            user.setAdmin(false);
            view.put("user", user);
            setMessage(request, "Sorry, you are no longer admin");
            throw new RedirectException("/users");
        }
    }
}
