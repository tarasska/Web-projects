package ru.itmo.wp.web.page;

import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class TalksPage extends Page {

    @Override
    protected void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);

        if (getUser() == null) {
            throw new RedirectException("/index");
        }

        view.put("users", getUserService().findAll());
    }

    private void send(Map<String, Object> view) throws ValidationException {
        long recipientUserId;
        try {
            recipientUserId = Long.parseLong(getRequest().getParameter("recipientUserId"));
        } catch (NumberFormatException e) {
            throw new ValidationException("Please, choose recipient");
        }

        getTalkService().save(getUser().getId(), recipientUserId, getRequest().getParameter("text"));

        refreshTalks(view);
        //throw new RedirectException("/talks");
    }

    private void refreshTalks(Map<String, Object> view) {
        view.put("userService", getUserService());
        view.put("talks", getTalkService().findByUserOrderedByCreationTime(getUser()));
    }
}
