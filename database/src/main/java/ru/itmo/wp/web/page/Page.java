package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.repository.TalkRepository;
import ru.itmo.wp.model.service.EventService;
import ru.itmo.wp.model.service.TalkService;
import ru.itmo.wp.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class Page {
    private final UserService userService = new UserService();
    private final EventService eventService = new EventService();
    private final TalkService talkService = new TalkService();
    private HttpServletRequest request;

    protected void action(Map<String, Object> view) {
        // No operations.
    }

    protected void before(HttpServletRequest request, Map<String, Object> view) {
        this.request = request;

        putUser(view);

        putUserCount(view);

        putMessage(view);
    }

    protected void after(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }

    protected void setMessage(String message) {
        request.getSession().setAttribute("message", message);
    }

    protected void setUser(User user) {
        request.getSession().setAttribute("user", user);
    }

    protected User getUser() {
        return (User) request.getSession().getAttribute("user");
    }

    private void putMessage(Map<String, Object> view) {
        String message = (String) request.getSession().getAttribute("message");
        if (!Strings.isNullOrEmpty(message)) {
            view.put("message", message);
            request.getSession().removeAttribute("message");
        }
    }

    private void putUserCount(Map<String, Object> view) {
        view.put("userCount", userService.findCount());
    }

    private void putUser(Map<String, Object> view) {
        User user = getUser();
        if (user != null) {
            view.put("user", user);
        }
    }

    protected UserService getUserService() {
        return userService;
    }

    protected HttpServletRequest getRequest() {
        return request;
    }

    public EventService getEventService() {
        return eventService;
    }

    public TalkService getTalkService() {
        return talkService;
    }
}
