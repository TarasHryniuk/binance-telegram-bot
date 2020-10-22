package bot.crypto.dao;

import bot.crypto.conf.DataSourceConfig;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Taras Hryniuk, created on  22.10.2020
 * email : hryniuk.t@gmail.com
 */
public class StatisticDao {

    private static final String SQL_INSERT = "INSERT INTO statistic (id, user_name, user_id, date, message_id, request) VALUES (DEFAULT, ?, ?, ?, ?, ?)";

    private static final Lock LOCK = new ReentrantLock();
    private static final Logger LOGGER = Logger.getLogger(StatisticDao.class);

    public boolean insertUser(String request, Integer userId, Integer messageId, String userName) {
        LOCK.lock();
        try {
            Connection connection = DataSourceConfig.getConnection();
            PreparedStatement ps = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, userName);
            ps.setInt(2, userId);
            ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            ps.setInt(4, messageId);
            ps.setString(5, request);
            if (ps.executeUpdate() != 1)
                return false;
        } catch (Exception e) {
            LOGGER.error(e);
            return false;
        } finally {
            try {
                LOCK.unlock();
            } catch (Exception e) {
                LOGGER.error(e);
            }
        }
        return true;
    }

}
