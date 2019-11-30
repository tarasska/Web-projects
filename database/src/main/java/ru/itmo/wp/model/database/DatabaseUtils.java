package ru.itmo.wp.model.database;

import org.mariadb.jdbc.MariaDbDataSource;
import ru.itmo.wp.model.exception.RepositoryException;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;


public class DatabaseUtils {
    public static DataSource getDataSource() {
        return DataSourceHolder.INSTANCE;
    }

    public static QueryResultHolder find(String query, int longArgumentsCount, String... args) throws SQLException {
        try (Connection connection = getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                setStatement(statement, longArgumentsCount, args);
                return new QueryResultHolder(statement.getMetaData(), statement.executeQuery());
            }
        }
    }

    public static ResultSet save(String query, String exceptionMessage, int longArgumentsCount, String... args)
            throws SQLException, RepositoryException {
        try (Connection connection = getDataSource().getConnection()) {
            try (PreparedStatement statement = connection
                    .prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                setStatement(statement, longArgumentsCount, args);
                if (statement.executeUpdate() != 1) {
                    throw new RepositoryException(exceptionMessage);
                } else {
                    return statement.getGeneratedKeys();
                }
            }
        }
    }

    private static void setStatement(PreparedStatement statement, int longArgumentsCount, String[] args) throws SQLException {
        for (int i = 0; i < longArgumentsCount; ++i) {
            statement.setLong(i + 1, Long.parseLong(args[i]));
        }
        for (int i = longArgumentsCount; i < args.length; ++i) {
            statement.setString(i + 1, args[i]);
        }
    }

    public static class QueryResultHolder implements AutoCloseable {
        private ResultSetMetaData resultSetMetaData;
        private ResultSet resultSet;

        public QueryResultHolder(ResultSetMetaData resultSetMetaData, ResultSet resultSet) {
            this.resultSetMetaData = resultSetMetaData;
            this.resultSet = resultSet;
        }

        public ResultSetMetaData getResultSetMetaData() {
            return resultSetMetaData;
        }

        public ResultSet getResultSet() {
            return resultSet;
        }


        @Override
        public void close() throws SQLException {
            if (!resultSet.isClosed()) {
                resultSet.close();
            }
        }
    }

    private static final class DataSourceHolder {
        private static final DataSource INSTANCE;
        private static final Properties properties = new Properties();

        static {
            try {
                properties.load(DataSourceHolder.class.getResourceAsStream("/application.properties"));
            } catch (IOException e) {
                throw new RuntimeException("Can't load /application.properties.", e);
            }

            try {
                MariaDbDataSource instance = new MariaDbDataSource();
                instance.setUrl(properties.getProperty("database.url"));
                instance.setUser(properties.getProperty("database.user"));
                instance.setPassword(properties.getProperty("database.password"));
                INSTANCE = instance;
            } catch (SQLException e) {
                throw new RuntimeException("Can't initialize DataSource.", e);
            }

            try (Connection connection = INSTANCE.getConnection()) {
                if (connection == null) {
                    throw new RuntimeException("Can't create testing connection via DataSource.");
                }
            } catch (SQLException e) {
                throw new RuntimeException("Can't create testing connection via DataSource.", e);
            }
        }
    }
}
