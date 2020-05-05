package com.company.jdbc;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SqlExecutor {

    private static final String SQL_PATH = "/home/teron/下载/corejava10/corejava/v2ch05/";
    public static void main(String[] args) {
        Path rootDirectory = Paths.get(SQL_PATH);
        if (!Files.isDirectory(rootDirectory)){
            return;
        }
        List<Path> fileNameList = new ArrayList<>();
        try (Connection connection = DataBaseUtil.getConnection();
             Statement stat = connection.createStatement()) {
            Files.walkFileTree(rootDirectory, new SimpleFileVisitor<Path>(){
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    String s = file.toString();
                    if (s.endsWith("sql")){
                        fileNameList.add(file);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
            for (Path fileName : fileNameList) {
                Scanner scanner = new Scanner(Files.newInputStream(fileName), "UTF-8");
                while (scanner.hasNextLine()) {
                    String sqlStr = scanner.nextLine();
                    boolean result = stat.execute(sqlStr);
                    if (result) {
                        try (ResultSet resultSet = stat.getResultSet()) {
                            DataBaseUtil.showResult(resultSet);
                        }
                    } else {
                        int updateCount = stat.getUpdateCount();
                        System.out.println("update count is " + updateCount);
                    }
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

    }


}
