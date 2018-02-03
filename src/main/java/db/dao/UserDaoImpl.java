package db.dao;

import db.connection.ConnectionManager;
import db.connection.ConnectionManagerPostgres;
import db.dao.exceptions.UserDaoException;
import db.dao.exceptions.UserDataDaoException;
import db.pojo.User;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
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
                    "public.user VALUES (DEFAULT, ?, ?, ?, ?, DEFAULT, DEFAULT, ?, DEFAULt); ");
            statement.setString(1, login);
            statement.setString(2, password);
            statement.setInt(3, id_user_data);
            statement.setString(4, date_reg);
            statement.setString(5, "role_user\n");
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

    @Override
    public String getRank(int id_user) throws UserDaoException {
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("SELECT " +
                    "s.name FROM public.user u INNER JOIN " +
                    "s_rank s ON s.id = u.id_rank " +
                    "WHERE u.id = ?"
            );
            statement.setInt(1, id_user);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                String rank = set.getString("name");
                connection.close();
                return rank;
            }
            return "";
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("exception in user dao");
            throw new UserDaoException();
        }
    }

    @Override
    public boolean checkPass(int id_user, String password) throws UserDaoException {
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("SELECT password " +
                    "FROM public.user WHERE id = ?"
            );
            statement.setInt(1, id_user);
            ResultSet set = statement.executeQuery();
            set.next();
            String pass = set.getString("password");
            connection.close();
            return password.equals(pass);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("exception in user dao");
            throw new UserDaoException();
        }
    }

    @Override
    public boolean editName(int id_user, String name) throws UserDaoException {
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("UPDATE " +
                    "user_data  SET name = ? WHERE id = (SELECT " +
                    "ud.id FROM public.user u INNER JOIN user_data ud ON ud.id = u.user_data_id " +
                    "WHERE u.id = ?);"
            );
            statement.setString(1, name);
            statement.setInt(2, id_user);

            int result = statement.executeUpdate();
            connection.close();
            return result == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("exception in user dao");
            throw new UserDaoException();
        }
    }

    @Override
    public boolean editLast(int id_user, String last) throws UserDaoException {
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("UPDATE " +
                    "user_data  SET last_name = ? WHERE id = (SELECT " +
                    "ud.id FROM public.user u INNER JOIN user_data ud ON ud.id = u.user_data_id " +
                    "WHERE u.id = ?);"
            );
            statement.setString(1, last);
            statement.setInt(2, id_user);

            int result = statement.executeUpdate();
            connection.close();
            return result == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("exception in user dao");
            throw new UserDaoException();
        }
    }
    @Override
    public List<User> topTenUsers() throws UserDaoException {
        Connection connection = connectionManager.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT " +
                    "u.login, u.level, s.name FROM public.user u INNER JOIN " +
                    "s_rank s ON s.id = u.id_rank ORDER BY level desc " +
                    "LIMIT 10");
            ResultSet set = preparedStatement.executeQuery();
            List<User> list = new ArrayList<>();
            while (set.next()) {
                User user = new User();
                user.setLogin(set.getString("login"));
                user.setLevel(set.getInt("level"));
                user.setRank(set.getString("name"));
                list.add(user);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("exception in user dao");
            throw new UserDaoException();
        }
    }


    @Override
    public User getLoginAndPassAndId(String username) throws UserDaoException {
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("SELECT " +
                    "id, login, password, role  " +
                    "from public.user u " +
                    "WHERE u.login = ?");
            statement.setString(1, username);
            User user = new User();
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                user.setId(set.getInt("id"));
                user.setLogin(set.getString("login"));
                user.setPassword(set.getString("password"));
                String roles = set.getString("role");
                String[] role = roles.split("\n");
                user.setRole(role);
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("exception in user dao");
            throw new UserDaoException();
        }
    }
}
