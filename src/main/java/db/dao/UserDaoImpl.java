package db.dao;

import db.connection.ConnectionManager;
import db.connection.ConnectionManagerPostgres;
import db.dao.exceptions.UserDaoException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
@Component
public class UserDaoImpl implements UserDao {

    public static ConnectionManager connectionManager = ConnectionManagerPostgres.getInstance();
    private static Logger logger = Logger.getLogger(UserDaoImpl.class);

    @Override
    public void reg(String login, String password, String date_reg, int id_user_data) throws UserDaoException {
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("INSERT INTO " +
                    "public.user VALUES (DEFAULT, ?, ?, ?, ?, DEFAULT, DEFAULT); ");
            statement.setString(1, login);
            statement.setString(2, password);
            statement.setInt(3, id_user_data);
            statement.setString(4, date_reg);
            statement.execute();
            connection.close();
        } catch (SQLException e) {
            logger.error("exception in user dao");
            throw new UserDaoException();
        }
    }

    @Override
    public int auth(String login, String password) throws UserDaoException {
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("SELECT " +
                    "id FROM " +
                    "public.user WHERE login = ? AND password = ?");
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                int res = set.getInt("id");
                connection.close();
                return res;
            } else {
                connection.close();
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("exception in user dao");
            throw new UserDaoException();
        }
    }

    @Override
    public void addSubCourseInProfil(int id_user, int id_sub_course, int id_status) throws UserDaoException {
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("INSERT INTO " +
                    "user_sub_course VALUES ( " +
                    "DEFAULT" +
                    ", ?" +
                    ", ?" +
                    ", ? " +
                    ", 0)");
            statement.setInt(1, id_user);
            statement.setInt(2, id_sub_course);
            statement.setInt(3, id_status);
            statement.execute();
            connection.close();
        } catch (SQLException e) {
            logger.error("exception in user dao");
            throw new UserDaoException();
        }
    }

    @Override
    public void changeCurrencyTaskOfUser(int id_course, int id_user, int id_next_task_by_course) throws UserDaoException {
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("UPDATE " +
                    "user_course SET id_currency_task = ? WHERE id_user = ? AND id_course = ?"
            );
            statement.setInt(1, id_next_task_by_course);
            statement.setInt(2, id_user);
            statement.setInt(3, id_course);
            statement.execute();
            connection.close();
        } catch (SQLException e) {
            logger.error("exception in user dao");
            throw new UserDaoException();
        }
    }

    @Override
    public int editPassword(int id_user, String pass) throws UserDaoException {
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("UPDATE " +
                    "public.user u SET password = ? WHERE u.id " +
                    "= ?"
            );
            statement.setInt(2, id_user);
            statement.setString(1, pass);

            int result = statement.executeUpdate();
            connection.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("exception in user dao");
            throw new UserDaoException();
        }
    }

    @Override
    public boolean checkOnUniqueLogin(String login) throws UserDaoException {
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("SELECT " +
                    "id FROM public.user " +
                    "WHERE login = ?"
            );
            statement.setString(1, login);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                System.out.println("такой логин есть");
                return false;
            }
            return true;
        } catch (SQLException e) {
            logger.error("exception in user dao");
            throw new UserDaoException();
        }
    }

}
