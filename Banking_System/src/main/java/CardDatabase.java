import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.ArrayList;

public class CardDatabase {
    private String url;
    private SQLiteDataSource dataSource;

    public CardDatabase(String u) {
        url = u;
        dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);
    }

    public String getUrl() {
        return url;
    }

    private Connection connect() {
        Connection con = null;
        try {
            con = dataSource.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return con;
    }

    public void createDatabase() {
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS card(" +
                        "id INTEGER PRIMARY KEY," +
                        "number TEXT NOT NULL," +
                        "pin TEXT(4) NOT NULL," +
                        "balance INTEGER DEFAULT 0)");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public int addString(String insert) {
        int i = -1;
        try (Connection connection = dataSource.getConnection()) {
            // Disable auto-commit mode
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement()) {
                i = statement.executeUpdate(insert);

            } catch (SQLException e) {
                e.printStackTrace();
            }

            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return i;
    }

    public int getBalance(String number) {
        int balance = -1;
        String select = "SELECT * FROM card WHERE number = " + number;
        try (Connection con = dataSource.getConnection();
             Statement statement = con.createStatement();
             ResultSet resultState = statement.executeQuery(select)) {

            // Retrieve column values
            if (resultState.getString("number").equals(number)) {
                balance = resultState.getInt("balance");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return balance;
    }

    public CreditCardInfo findCard(String number, String pin) {  // возвращает баланс
        String select = "SELECT * FROM card WHERE number = " + number + " and pin = " + pin;
        int rows = 0;
        CreditCardInfo card = null;
        try (Connection con = dataSource.getConnection();
             Statement statement = con.createStatement();
             ResultSet resultSet = statement.executeQuery(select)) {


            if (resultSet.next()) {
                int balance = resultSet.getInt("balance");
                ArrayList<Integer> arrayNumber = new ArrayList<>();
                for (int i = 0; i < number.length(); i++) {
                    arrayNumber.add(Integer.parseInt(number.charAt(i) + ""));
                }

                ArrayList<Integer> arrayPin = new ArrayList<>();
                for (int i = 0; i < pin.length(); i++) {
                    arrayPin.add(Integer.parseInt(pin.charAt(i) + ""));
                }

                card = new CreditCardInfo(arrayNumber, arrayPin, balance);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return card;
    }

    public CreditCardInfo findCardForTransfer(String number) {  // возвращает баланс
        String select = "SELECT * FROM card WHERE number = " + number;
        CreditCardInfo card = null;
        try (Connection con = dataSource.getConnection();
             Statement statement = con.createStatement();
             ResultSet resultSet = statement.executeQuery(select)) {


            if (resultSet.next()) {
                int balance = resultSet.getInt("balance");
                String pin = resultSet.getString("pin");

                ArrayList<Integer> arrayNumber = new ArrayList<>();
                for (int i = 0; i < number.length(); i++) {
                    arrayNumber.add(Integer.parseInt(number.charAt(i) + ""));
                }

                ArrayList<Integer> arrayPin = new ArrayList<>();
                for (int i = 0; i < pin.length(); i++) {
                    arrayPin.add(Integer.parseInt(pin.charAt(i) + ""));
                }

                card = new CreditCardInfo(arrayNumber, arrayPin, balance);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return card;
    }


    public int countRows() {
        String select = "Select * from card";
        int rows = 0;
        try (Connection con = dataSource.getConnection();
             Statement statement = con.createStatement();
             ResultSet resultSet = statement.executeQuery(select)) {

            while(resultSet.next()) {
                rows++;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return rows;
    }

    public void addIncome(int income, String cardNumber) {  // пополнение счета
        String update = "UPDATE card " +
                "SET balance = ? " +
                "WHERE number = " + cardNumber;
        int balance = getBalance(cardNumber) + income;

        int i = 0;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(update)) {
            statement.setInt(1, balance);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeAccount(String cardNumber) {  // закрытие аккаунта (удаление)
        String str = "DELETE FROM card WHERE number = " + cardNumber;
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(str);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}