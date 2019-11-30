package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/** @noinspection unused*/
public class ArticlePage extends Page {
    private final ArticleService articleService = new ArticleService();

    protected void action(HttpServletRequest request, Map<String, Object> view) {
        super.action(request, view);
        if (request.getSession().getAttribute("user") == null) {
            request.getSession().setAttribute("message", "Log in to publish articles");
            throw new RedirectException("/index");
        }
    }

    private void createArticle(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        User user = getUser(request);

        String title = request.getParameter("title");
        String text = request.getParameter("text");

        articleService.validate(title, text);

        articleService.save(user.getId(), false, title, text);

        setMessage(request, "Article published");

        throw new RedirectException("/article");
    }
}
