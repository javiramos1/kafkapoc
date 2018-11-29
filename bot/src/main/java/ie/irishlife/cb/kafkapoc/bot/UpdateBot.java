package ie.irishlife.cb.kafkapoc.bot;

import java.sql.*;

/**
 * Class that ramdomly generates updates
 */
public class UpdateBot {

    private static final String JDBC_URL = "jdbc:oracle:thin:@localhost:49161:xe";
    private static final String USER = "system";
    private static final String PASS = "oracle";


    public static void main(String[] args) {

        if (registerDriver()) return;


        try {

            final Connection connection = getConnection();

            int i = 1;

            while(true)
            {

                try {

                    Statement stmt = null;
                    String query =
                            "select MARITAL_STATUS from system.member where clientid = '" + (i++) + "'";

                    stmt = connection.createStatement();
                    stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs = stmt.executeQuery(query);
                    while (rs.next()) {
                        updateResult(rs);
                    }

                    Thread.sleep(19000); // wait
                } catch (InterruptedException e) {
                    break;
                }
            }



        } catch (SQLException e) {

            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();

            return;

        }



    }

    private static void updateResult(ResultSet rs) throws SQLException {
        String status = rs.getString("MARITAL_STATUS");
        System.out.println("status " + status);
        status = (status.equals("S") ? "M" : "S");
        rs.updateString("MARITAL_STATUS", status);
        rs.updateRow();
    }

    private static Connection getConnection() throws SQLException {
        final Connection  connection = DriverManager.getConnection(
                JDBC_URL, USER, PASS);

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                System.out.println("Clossing");
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }) );
        return connection;
    }

    private static boolean registerDriver() {
        System.out.println("-------- Oracle JDBC Connection Testing ------");

        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");

        } catch (ClassNotFoundException e) {

            System.out.println("Where is your Oracle JDBC Driver?");
            e.printStackTrace();
            return true;

        }

        System.out.println("Oracle JDBC Driver Registered!");
        return false;
    }


}
