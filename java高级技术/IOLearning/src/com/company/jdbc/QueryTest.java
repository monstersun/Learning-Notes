package com.company.jdbc;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QueryTest {

    private static final String ANY = "Any";
    private static final String ALL_QUERY = "SELECT Books.Price, Books.Title FROM Books";

    private static final String AUTHOR_PUBLISHER_QUERY = "SELECT Books.Price, Books.Title"
            + " FROM Books, BooksAuthors, Authors, Publishers"
            + " WHERE Authors.Author_Id = BooksAuthors.Author_Id AND BooksAuthors.ISBN = Books.ISBN"
            + " AND Books.Publisher_Id = Publishers.Publisher_Id AND Authors.Name = ?"
            + " AND Publishers.Name = ?";

    private static final String AUTHOR_QUERY
            = "SELECT Books.Price, Books.Title FROM Books, BooksAuthors, Authors"
            + " WHERE Authors.Author_Id = BooksAuthors.Author_Id AND BooksAuthors.ISBN = Books.ISBN"
            + " AND Authors.Name = ?";

    private static final String PUBLISHER_QUERY
            = "SELECT Books.Price, Books.Title FROM Books, Publishers"
            + " WHERE Books.Publisher_Id = Publishers.Publisher_Id AND Publishers.Name = ?";


    private static final String PRICE_UPDATE = "UPDATE Books " + "SET Price = Price + ? "
            + " WHERE Books.Publisher_Id = (SELECT Publisher_Id FROM Publishers WHERE Name = ?)";

    private static Scanner in;
    private static List<String> authors = new ArrayList<>();
    private static List<String> publishers = new ArrayList<>();

    public static void main(String[] args) {
        try (Connection conn = DataBaseUtil.getConnection();) {
            in = new Scanner(System.in);
            authors.add(ANY);
            publishers.add(ANY);
            try (Statement stat = conn.createStatement()) {
                String query = "SELECT Name FROM Authors";
                try (ResultSet rs = stat.executeQuery(query)) {
                    while (rs.next()) {
                        authors.add(rs.getString(1));
                    }
                }
                query = "SELECT Name FROM Publishers";
                try (ResultSet rs = stat.executeQuery(query)) {
                    while (rs.next()) {
                        publishers.add(rs.getString(1));
                    }
                }
                boolean done = false;
                while (!done) {
                    System.out.println("Q)uery C)hange prices E)XIT:");
                    String input = in.next();
                    switch (input) {
                        case "Q":
                            executeQuery(conn);
                            break;
                        case "C":
                            changePrice(conn);
                            break;
                        case "E":
                            done = true;
                            break;
                        default:
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void changePrice(Connection conn) throws SQLException {
        PreparedStatement stat;
        String publisher = select("Publishers: ", publishers.subList(1, publishers.size()));
        System.out.println("Change price by");
        double price = in.nextDouble();
        stat = conn.prepareStatement(PRICE_UPDATE);
        stat.setDouble(1, price);
        stat.setString(2, publisher);
        int i = stat.executeUpdate();
        System.out.println(i + "records has been changed");
    }

    private static void executeQuery(Connection conn) throws SQLException {
        String author = select("Authors: ", authors);
        String publisher = select("Publishers: ", publishers);
        PreparedStatement stat;
        if (!author.equalsIgnoreCase(ANY) && !publisher.equalsIgnoreCase(ANY)) {
            stat = conn.prepareStatement(AUTHOR_PUBLISHER_QUERY);
            stat.setString(1, author);
            stat.setString(1, publisher);
        } else if (author.equalsIgnoreCase(ANY) && !publisher.equalsIgnoreCase(ANY)) {
            stat = conn.prepareStatement(PUBLISHER_QUERY);
            stat.setString(1, publisher);
        } else if (!author.equalsIgnoreCase(ANY) && publisher.equalsIgnoreCase(ANY)){
            stat = conn.prepareStatement(AUTHOR_QUERY);
            stat.setString(1, author);
        }else{
            stat = conn.prepareStatement(ALL_QUERY);
        }
        ResultSet resultSet = stat.executeQuery();
        DataBaseUtil.showResult(resultSet);
    }

    private static String select(String prompt, List<String> options) {
        while (true) {
            System.out.println(prompt);
            for (int i = 0; i < options.size(); i++) {
                System.out.printf("%2d) %s%n", i + 1, options.get(i));
            }
            int sel = in.nextInt();
            if (sel > 0 && sel <= options.size()) {
                return options.get(sel - 1);
            }
        }
    }
}
