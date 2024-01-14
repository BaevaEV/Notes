package ui.db;

import java.sql.*;
import java.util.concurrent.TimeUnit;

public class DBConnection {

    private Connection connection;

    // Конструктор, инициализирующий соединение
    public DBConnection() {
        try {
            String url = "jdbc:postgresql://172.24.120.5:5432/postgres";
            String login = "root";
            String password = "root";
            connection = DriverManager.getConnection(url, login, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Метод выполнения обычного запроса
    public void executeQuery(String sql) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Метод выполнения параметризованного запроса
    public String executeParameterizedQuery(String sql, String columnName, Object parameterValue) {
        int rowsAffected = 0;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            setParameter(statement, 1, parameterValue);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString(columnName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Метод для установки параметра в зависимости от его типа
    private void setParameter(PreparedStatement statement, int index, Object parameterValue) throws SQLException {
        if (parameterValue instanceof Long) {
            statement.setLong(index, (Long) parameterValue);
        } else if (parameterValue instanceof String) {
            statement.setString(index, (String) parameterValue);
        } else {
            throw new IllegalArgumentException("Unsupported parameter type: " + parameterValue.getClass());
        }
    }

    public String executeParameterizedQueryWithWait(String sql, String columnName, Object parameterValue, long waitTimeInMillSeconds) {

        try {
            TimeUnit.MILLISECONDS.sleep(waitTimeInMillSeconds);
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                setParameter(statement, 1, parameterValue);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString(columnName);
                    }
                }
            }
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public int executeParameterizedUpdateWithWait(String sql, Object parameterValue, long waitTimeInMillSeconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(waitTimeInMillSeconds);
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                setParameter(statement, 1, parameterValue);
                return statement.executeUpdate();
            }
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        }
        return -1; // Возвращаем какое-то значение по умолчанию или обработку ошибки.
    }

    // Закрытие соединения
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
