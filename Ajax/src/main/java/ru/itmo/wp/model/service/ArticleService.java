package ru.itmo.wp.model.service;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.ArticleRepository;
import ru.itmo.wp.model.repository.impl.ArticleRepositoryImpl;

import java.util.List;

public class ArticleService {
    private final ArticleRepository articleRepository = new ArticleRepositoryImpl();

    public Article find(long id) {
        return articleRepository.find(id);
    }

    public void save(long userId, boolean hidden, String title, String text) {
        Article article = new Article();
        article.setUserId(userId);
        article.setHidden(hidden);
        article.setTitle(title);
        article.setText(text);

        articleRepository.save(article);
    }

    public void validate(String title, String text) throws ValidationException {
        if (title == null || title.isEmpty()) {
            throw new ValidationException("The empty title is prohibited");
        }

        if (text == null || text.isEmpty()) {
            throw new ValidationException("The empty article is prohibited");
        }
    }

    public List<Article> findAllOrderedByCreationTime() {
        return articleRepository.findAllOrderedByCreationTime();
    }

    public List<Article> findAllByUserOrderedByCreationTime(User user) {
        return articleRepository.findAllByUserOrderedByCreationTime(user);
    }

    public void setHidden(long id, boolean hidden) {
        articleRepository.setHidden(id, hidden);
    }
}
