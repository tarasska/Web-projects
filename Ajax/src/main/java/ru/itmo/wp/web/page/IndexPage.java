package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/** @noinspection unused*/
public class IndexPage extends Page {
    private final ArticleService articleService = new ArticleService();
    private final UserService userService = new UserService();


    protected void action(HttpServletRequest request, Map<String, Object> view) {
        super.action(request, view);
    }

    private void findAllArticles(HttpServletRequest request, Map<String, Object> view) {
        List<Article> articles = articleService.findAllOrderedByCreationTime();
        view.put("articles", articles);
    }


    private void findAll(HttpServletRequest request, Map<String, Object> view) {
        view.put("users", userService.findAll());
    }

    private void findUser(HttpServletRequest request, Map<String, Object> view) {
        view.put("user",
                userService.find(Long.parseLong(request.getParameter("userId"))));
    }

    private void findUserLoginById(HttpServletRequest request, Map<String, Object> view) {
        if (request.getParameter("userId") != null) {
            view.put("userLogin",  userService.find(Long.parseLong(request.getParameter("userId"))).getLogin());
        }
    }
}
