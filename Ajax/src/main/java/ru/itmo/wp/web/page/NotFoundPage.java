package ru.itmo.wp.web.page;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/** @noinspection unused*/
public class NotFoundPage extends Page {
    protected void action(HttpServletRequest request, Map<String, Object> view) {
        super.action(request, view);
        // No operations.
    }
}
