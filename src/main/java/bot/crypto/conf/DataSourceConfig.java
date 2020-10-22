package bot.crypto.conf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Taras Hryniuk, created on  22.10.2020
 * email : hryniuk.t@gmail.com
 */
public class DataSourceConfig {

    private static Connection connection;

    private static String url = "jdbc:postgresql://localhost:5432/telegram_bot";
    private static String user = "postgres";
    private static String password = "postgres";

    private DataSourceConfig() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws Exception {
        if (connection == null) {
            connection = DriverManager.getConnection(url, user, password);
        }

        return connection;
    }

}
