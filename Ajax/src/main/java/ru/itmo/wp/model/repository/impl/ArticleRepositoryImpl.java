package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.database.DatabaseUtils.*;
import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.repository.ArticleRepository;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArticleRepositoryImpl implements ArticleRepository {
    @Override
    public Article find(long id) {
        try (QueryResultHolder queryResultHolder
                     = DatabaseUtils.find("SELECT * FROM Article WHERE id=?", 1,
                String.valueOf(id))) {
            return toArticle(queryResultHolder.getResultSetMetaData(), queryResultHolder.getResultSet());
        } catch (SQLException e) {
            throw new RepositoryException("Can't find Article.", e);
        }
    }

    @Override
    public List<Article> findAllOrderedByCreationTime() {
        List<Article> articles = new ArrayList<>();
        try (DatabaseUtils.QueryResultHolder queryResultHolder = DatabaseUtils.find(
                "SELECT * FROM Article ORDER BY creationTime DESC, id DESC",
                0)) {
            Article article;
            while ((article = toArticle(queryResultHolder.getResultSetMetaData(), queryResultHolder.getResultSet())) != null) {
                articles.add(article);
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find Article.", e);
        }
        return articles;
    }

    @Override
    public List<Article> findAllByUserOrderedByCreationTime(User user) {
        List<Article> articles = new ArrayList<>();
        try (DatabaseUtils.QueryResultHolder queryResultHolder = DatabaseUtils.find(
                "SELECT * FROM Article WHERE userId=? ORDER BY creationTime DESC, id DESC",
                1,
                String.valueOf(user.getId()))) {
            Article article;
            while ((article = toArticle(queryResultHolder.getResultSetMetaData(), queryResultHolder.getResultSet())) != null) {
                articles.add(article);
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find Article.", e);
        }
        return articles;
    }

    @Override
    public void save(Article article) {
        try (ResultSet generatedKeys = DatabaseUtils.save(
                "INSERT INTO `Article` (`userId`, `hidden`, `title`, `text`, `creationTime`) VALUES (?, ?, ?, ?, NOW())",
                "Can't save Article.",
                2,
                String.valueOf(article.getUserId()),
                article.isHidden() ? "1" : "0",
                article.getTitle(),
                article.getText())) {
            if (generatedKeys.next()) {
                article.setId(generatedKeys.getLong(1));
                article.setCreationTime(find(article.getId()).getCreationTime());
            } else {
                throw new RepositoryException("Can't save Article [no autogenerated fields].");
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't save Article.", e);
        }
    }

    @Override
    public void setHidden(long id, boolean hidden) {
        try {
            DatabaseUtils.update(
                    "UPDATE Article SET hidden=? WHERE id=?",
                    2,
                    hidden ? "1" : "0",
                    String.valueOf(id));
        } catch (SQLException e) {
            throw new RepositoryException("Can't update Article.", e);
        }
    }


    private Article toArticle(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        Article article = new Article();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    article.setId(resultSet.getLong(i));
                    break;
                case "userId":
                    article.setUserId(resultSet.getLong(i));
                    break;
                case "hidden":
                    article.setHidden(resultSet.getBoolean(i));
                    break;
                case  "title":
                    article.setTitle(resultSet.getString(i));
                    break;
                case "text":
                    article.setText(resultSet.getString(i));
                    break;
                case "creationTime":
                    article.setCreationTime(resultSet.getTimestamp(i));
                    break;
                default:
                    // No operations.
            }
        }

        return article;
    }
}