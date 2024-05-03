package ui.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
    public void executeQuery(String filePath) {
        String sql = readSQLQueryFromFile(filePath);
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Метод выполнения параметризованного запроса
    public String executeParameterizedQuery(String filePath, String columnName, Object parameterValue) {
        String sql = readSQLQueryFromFile(filePath);
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

    public String executeParameterizedQueryWithWait(String filePath, String columnName, Object parameterValue, long waitTimeInMillSeconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(waitTimeInMillSeconds);
            String sql = readSQLQueryFromFile(filePath);
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

    public int executeParameterizedUpdateWithWait(String filePath, Object parameterValue, long waitTimeInMillSeconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(waitTimeInMillSeconds);
            String sql = readSQLQueryFromFile(filePath);
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                setParameter(statement, 1, parameterValue);
                return statement.executeUpdate();
            }
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        }
        return -1; // Возвращаем какое-то значение по умолчанию или обработку ошибки.
    }

    public String readSQLQueryFromFile(String filePath) {
        try (InputStream inputStream = getClass().getResourceAsStream(filePath)) {
            assert inputStream != null;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                return reader.lines().collect(Collectors.joining("\n"));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
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
