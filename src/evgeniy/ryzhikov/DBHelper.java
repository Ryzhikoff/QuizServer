package evgeniy.ryzhikov;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;

public class DBHelper {

    Connection connection;
    Statement statement;


    //Соединение с БД
    public void open () {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + Constants.PATH_DB);
            System.out.println("Connected");

            statement = connection.createStatement();


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public int getCountString () {
        String query = "select count(*) from " + Constants.NAME_TABLE;
        //Executing the query
        ResultSet rs = null;
        int count = 0;
        try {
            rs = statement.executeQuery(query);
            rs.next();
            count = rs.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            return count;
        }

    }

    public ResultSet getRandomRow (int count) {
        String query = "SELECT * FROM " +Constants.NAME_TABLE + " ORDER BY RANDOM() LIMIT " + count +";";
        ResultSet rs = null;
        try {
            rs = statement.executeQuery(query);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            return rs;
        }

    }

    //закрываем БД
    public void close () {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    private void readFile () {
        try {
            FileReader reader = new FileReader(new File(Constants.PATH_BASE));
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = null;

            /*while ((line = bufferedReader.readLine()) != null) {
                String[] nl = line.split("\\|");
                String query =
                        "INSERT INTO questions (question, trueanswer, answer2, answer3, answer4) " +
                        "VALUES ('" + nl[0] + "', '" + nl[1] + "', '" + nl[2] + "', '" + nl[3] + "', '" + nl[4] + "')";
                Statement statement = connection.createStatement();
                statement.executeUpdate(query);
                System.out.println(query);

            }*/
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
