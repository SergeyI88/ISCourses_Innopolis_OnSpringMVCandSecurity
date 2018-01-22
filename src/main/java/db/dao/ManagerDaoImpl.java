package db.dao;

import db.connection.ConnectionManager;
import db.connection.ConnectionManagerPostgres;
import db.dao.exceptions.CourseDaoException;
import db.dao.exceptions.ManagerDaoException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
@Component
public class ManagerDaoImpl implements ManagerDao{
    private static ConnectionManager connectionManager = ConnectionManagerPostgres.getInstance();
    private static Logger logger = Logger.getLogger(ManagerDaoImpl.class);


    @Override
    public int auth(String login, String password) throws ManagerDaoException {
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("SELECT " +
                    "id FROM " +
                    "manager WHERE login = ? AND password = ?");
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet set = statement.executeQuery();
            set.next();
            connection.close();
            return set.getInt("id");
        } catch (SQLException e) {
            logger.error("exception dao course");
            throw new ManagerDaoException();
        }
    }
}
