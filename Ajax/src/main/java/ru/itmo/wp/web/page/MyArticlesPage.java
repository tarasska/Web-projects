package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class MyArticlesPage extends Page {
    private final ArticleService articleService = new ArticleService();

    protected void action(HttpServletRequest request, Map<String, Object> view) {
        super.action(request, view);
        if (getUser(request) == null) {
            setMessage(request, "Log in to see your articles");
            throw new RedirectException("/index");
        }
        view.put("articles", articleService.findAllByUserOrderedByCreationTime(getUser(request)));
    }

    private void changeVisibility(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        User user = getUser(request);
        if (user == null) {
            request.getSession().setAttribute("message", "Log in to change your articles");
            throw new RedirectException("/index");
        }
        long articleId;
        try {
            articleId = Long.parseLong(request.getParameter("articleId"));
        } catch (NumberFormatException e) {
            throw new ValidationException("Something went wrong");
        }

        Article article = articleService.find(articleId);

        if (article.getUserId() != user.getId()) {
            setMessage(request, "You are not owner of this article");
            throw new RedirectException("/index");
        }

        boolean newHidden = !article.isHidden();

        articleService.setHidden(articleId, newHidden);

        view.put("newVisibility", newHidden ? "Show" : "Hide");
    }
}
