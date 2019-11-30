package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.database.DatabaseUtils.QueryResultHolder;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.repository.UserRepository;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    @Override
    public User find(long id) {
        try (QueryResultHolder queryResultHolder
                     = DatabaseUtils.find("SELECT * FROM User WHERE id=?", 1,
                String.valueOf(id))) {
            return toUser(queryResultHolder.getResultSetMetaData(), queryResultHolder.getResultSet());
        } catch (SQLException e) {
            throw new RepositoryException("Can't find User.", e);
        }
    }

    @Override
    public User findByLogin(String login) {
        try (QueryResultHolder queryResultHolder
                     = DatabaseUtils.find("SELECT * FROM User WHERE login=?", 0, login)) {
            return toUser(queryResultHolder.getResultSetMetaData(), queryResultHolder.getResultSet());
        } catch (SQLException e) {
            throw new RepositoryException("Can't find User.", e);
        }
    }

    @Override
    public User findByEmail(String email) {
        try (QueryResultHolder queryResultHolder
                     = DatabaseUtils.find("SELECT * FROM User WHERE email=?", 0, email)) {
            return toUser(queryResultHolder.getResultSetMetaData(), queryResultHolder.getResultSet());
        } catch (SQLException e) {
            throw new RepositoryException("Can't find User.", e);
        }
    }

    @Override
    public User findByLoginAndPasswordSha(String login, String passwordSha) {
        try (QueryResultHolder queryResultHolder
                     = DatabaseUtils.find("SELECT * FROM User WHERE login=? AND passwordSha=?",
                0, login, passwordSha)) {
            return toUser(queryResultHolder.getResultSetMetaData(), queryResultHolder.getResultSet());
        } catch (SQLException e) {
            throw new RepositoryException("Can't find User.", e);
        }
    }

    @Override
    public User findByEmailAndPasswordSha(String email, String passwordSha) {
        try (QueryResultHolder queryResultHolder
                     = DatabaseUtils.find("SELECT * FROM User WHERE email=? AND passwordSha=?",
                0, email, passwordSha)) {
            return toUser(queryResultHolder.getResultSetMetaData(), queryResultHolder.getResultSet());
        } catch (SQLException e) {
            throw new RepositoryException("Can't find User.", e);
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (QueryResultHolder queryResultHolder
                     = DatabaseUtils.find("SELECT * FROM User ORDER BY id DESC", 0)) {
            User user;
            while ((user = toUser(queryResultHolder.getResultSetMetaData(), queryResultHolder.getResultSet())) != null) {
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find User.", e);
        }
        return users;
    }

    @Override
    public long findCount() {
        try (QueryResultHolder queryResultHolder
                     = DatabaseUtils.find("SELECT COUNT(*) FROM User", 0)) {
            if (queryResultHolder.getResultSet().next()) {
                return queryResultHolder.getResultSet().getLong(1);
            } else {
                throw new RepositoryException("Can't count the numbers of users.");
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find User.", e);
        }
    }

    private User toUser(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        User user = new User();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    user.setId(resultSet.getLong(i));
                    break;
                case "admin":
                    user.setAdmin(resultSet.getBoolean(i));
                    break;
                case "login":
                    user.setLogin(resultSet.getString(i));
                    break;
                case "email":
                    user.setEmail(resultSet.getString(i));
                    break;
                case "creationTime":
                    user.setCreationTime(resultSet.getTimestamp(i));
                    break;
                default:
                    // No operations.
            }
        }

        return user;
    }

    @Override
    public void save(User user, String passwordSha) {
        try (ResultSet generatedKeys = DatabaseUtils.save(
                "INSERT INTO `User` (`admin`, `login`, `email`, `passwordSha`, `creationTime`) VALUES (?, ?, ?, ?, NOW())",
                "Can't save User.",
                1,
                user.isAdmin() ? "1" : "0",
                user.getLogin(),
                user.getEmail(),
                passwordSha)) {
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getLong(1));
                user.setCreationTime(find(user.getId()).getCreationTime());
            } else {
                throw new RepositoryException("Can't save User [no autogenerated fields].");
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't save User.", e);
        }
    }

    @Override
    public void setAdmin(long id, boolean admin) {
        try {
            DatabaseUtils.update(
                    "UPDATE User SET admin=? WHERE id=?",
                    2,
                    admin ? "1" : "0",
                    String.valueOf(id));
        } catch (SQLException e) {
            throw new RepositoryException("Can't update User.", e);
        }
    }
}
