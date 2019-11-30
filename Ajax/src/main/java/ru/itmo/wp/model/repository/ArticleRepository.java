package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;

import java.util.List;

public interface ArticleRepository {
    Article find(long id);

    List<Article> findAllOrderedByCreationTime();

    List<Article> findAllByUserOrderedByCreationTime(User user);

    void save(Article article);

    void setHidden(long id, boolean hidden);
}
