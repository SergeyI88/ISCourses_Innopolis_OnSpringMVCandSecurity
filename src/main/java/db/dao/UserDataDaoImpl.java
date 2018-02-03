package db.dao;

import db.connection.ConnectionManager;
import db.connection.ConnectionManagerPostgres;
import db.dao.exceptions.UserDataDaoException;
import db.pojo.User;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
@Component
public class UserDataDaoImpl implements UserDataDao {

    private static ConnectionManager connectionManager = ConnectionManagerPostgres.getInstance();
    private static Logger logger = Logger.getLogger(UserDataDaoImpl.class);

    @Override
    public int reg(String name, String last_name, String birthday) throws UserDataDaoException {
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("INSERT INTO " +
                    "user_data VALUES (DEFAULT , ?, ?, ?)");
            statement.setString(1, name);
            statement.setString(2, last_name);
            statement.setString(3, birthday);
            statement.execute();
            Connection connection1 = connectionManager.getConnection();
            PreparedStatement statement1 = connection1.prepareStatement("SELECT " +
                    "id " +
                    "FROM " +
                    "user_data WHERE name = ? AND last_name = ? AND birthday = ?");
            statement1.setString(1, name);
            statement1.setString(2, last_name);
            statement1.setString(3, birthday);
            ResultSet set = statement1.executeQuery();
            set.next();
            int id = set.getInt("id");
            connection.close();
            connection1.close();
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("exception in userdata dao");
            throw new UserDataDaoException();
        }
    }

    @Override
    public void editName(int id_user, String name) throws UserDataDaoException {
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("UPDATE " +
                    "user_data SET name = ? WHERE id = " +
                    "(SELECT ud.id " +
                    "FROM user_data ud " +
                    "INNER JOIN public.user u ON u.user_data_id = ud.id " +
                    "WHERE u.id = ?)");
            statement.setString(1, name);
            statement.setInt(2, id_user);

            statement.executeQuery();
            connection.close();
        } catch (SQLException e) {
            logger.error("exception in userdata dao");
            throw new UserDataDaoException();
        }

    }

    @Override
    public void editLastName(int id_user, String last_name) throws UserDataDaoException {
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("UPDATE " +
                    "user_data SET last_name = ? WHERE id = " +
                    "(SELECT ud.id " +
                    "FROM user_data ud " +
                    "INNER JOIN public.user u ON u.user_data_id = ud.id " +
                    "WHERE u.id = ?)");
            statement.setString(1, last_name);
            statement.setInt(2, id_user);

            statement.executeQuery();
            connection.close();
        } catch (SQLException e) {
            logger.error("exception in userdata dao");
            throw new UserDataDaoException();
        }
    }

    @Override
    public String getNameUserDataFromIdUser(int id_user) throws UserDataDaoException {
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("SELECT " +
                    "ud.name FROM user_data ud " +
                    "INNER JOIN public.user u ON u.user_data_id = ud.id " +
                    "WHERE u.id = ?");
            statement.setInt(1, id_user);
            ResultSet set = statement.executeQuery();
            set.next();
            String name = set.getString("name");
            System.out.println(name);
            connection.close();
            return name;
        } catch (SQLException e) {
            logger.error("exception in userdata dao");
            throw new UserDataDaoException();
        }
    }

    @Override
    public String getLastNameUserDataFromIdUser(int id_user) throws UserDataDaoException {
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("SELECT " +
                    "ud.last_name FROM user_data ud " +
                    "INNER JOIN public.user u ON u.user_data_id = ud.id " +
                    "WHERE u.id = ?");
            statement.setInt(1, id_user);
            ResultSet set = statement.executeQuery();
            set.next();
            String lastName = set.getString("last_name");
            connection.close();
            System.out.println(lastName);
            return lastName;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("exception in userdata dao");
            throw new UserDataDaoException();
        }
    }

    @Override
    public int countTasks(int id_user) throws UserDataDaoException {
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("SELECT " +
                    "level " +
                    "from public.user u " +
                    "WHERE u.id = ?");
            statement.setInt(1, id_user);
            ResultSet set = statement.executeQuery();
            set.next();
            int currencyTasks  = set.getInt("level");
            connection.close();
            Connection connection2 = connectionManager.getConnection();
            PreparedStatement statement2 = connection2.prepareStatement("UPDATE public.user u " +
                    "SET level = ? " +
                    "WHERE u.id = ?");
            statement2.setInt(1, ++currencyTasks);
            statement2.setInt(2, id_user);
            statement2.executeUpdate();
            connection2.close();
            return currencyTasks;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("exception in userdata dao");
            throw new UserDataDaoException();
        }
    }

    @Override
    public boolean upRank(int id_user) throws UserDataDaoException {
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("SELECT " +
                    "id_rank " +
                    "from public.user u " +
                    "WHERE u.id = ?");
            statement.setInt(1, id_user);
            ResultSet set = statement.executeQuery();
            set.next();
            int currencyRank  = set.getInt("id_rank");
            connection.close();
            Connection connection2 = connectionManager.getConnection();
            PreparedStatement statement2 = connection2.prepareStatement("UPDATE public.user u " +
                    "SET id_rank = ? " +
                    "WHERE u.id = ?");
            statement2.setInt(1, ++currencyRank);
            statement2.setInt(2, id_user);
            int result = statement2.executeUpdate();
            connection2.close();
            return result == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("exception in userdata dao");
            throw new UserDataDaoException();
        }
    }
}
