package com.company.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;

public class DataBaseUtil {
    private DataBaseUtil() {
    }

    private static Properties prop = new Properties();
    private static final String DB_INFO_PATH = "src/dbinfo.properties";

    static {
        try (InputStream in = Files.newInputStream(Paths.get(DB_INFO_PATH))) {
            prop.load(in);
        } catch (IOException e) {
            // todo print to log
            System.out.println("something wrong with load database properties");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {

        String driver = prop.getProperty("db.driver");
        if (driver != null) {
            System.setProperty("jdbc.driver", driver);
        }
        String user = prop.getProperty("db.username");
        String password = prop.getProperty("db.password");
        String url = prop.getProperty("db.url");
        return DriverManager.getConnection(url, user, password);
    }

    public static void showResult(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        for (int i = 1;i <= columnCount;i++){
            if (i > 1) {
                System.out.print(", ");
            }
            System.out.print(metaData.getColumnLabel(i));
        }
        System.out.println();
        while (resultSet.next()) {
            for (int i = 1;i <= columnCount;i++){
                if (i > 1) {
                    System.out.print(", ");
                }
                System.out.print(resultSet.getString(i));
            }
            System.out.println();
        }
    }
}
