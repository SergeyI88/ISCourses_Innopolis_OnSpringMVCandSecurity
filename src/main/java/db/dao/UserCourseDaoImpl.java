package db.dao;

import db.connection.ConnectionManager;
import db.connection.ConnectionManagerPostgres;
import db.dao.exceptions.TaskCourseDaoException;
import db.dao.exceptions.UserCourseDaoException;
import db.pojo.UserCourse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
@Component
public class UserCourseDaoImpl implements UserCourseDao {
    private static ConnectionManager connectionManager = ConnectionManagerPostgres.getInstance();
    private static Logger logger = Logger.getLogger(UserCourseDaoImpl.class);

    @Override
    public boolean checkStatusByCourseAndUser(Integer id, int idCourse) throws UserCourseDaoException {
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("SELECT id_status " +
                    "FROM user_course WHERE id_user = ? AND id_course = ?");
            statement.setInt(1, id);
            statement.setInt(2, idCourse);
            ResultSet set = statement.executeQuery();
            boolean result = set.next();
            if (!result) {
                return false;
            }
            int res = set.getInt("id_status");
            System.out.println(res + " cstyatus");
            return 2 != res;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("exception in usercourse dao");
            throw new UserCourseDaoException();
        }
    }

    @Override
    public void changeStatusCourseByUser(Integer id, int idCourse) throws UserCourseDaoException {
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("UPDATE " +
                    "user_course SET id_status = 2 WHERE id_user = ? AND id_course = ?");
            statement.setInt(1, id);
            statement.setInt(2, idCourse);
            statement.execute();
        } catch (SQLException e) {
            logger.error("exception in usercourse dao");
            throw new UserCourseDaoException();
        }
    }

    @Override
    public int getCurrencyIdOfUser(Integer id, int idCourse) throws UserCourseDaoException {
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("SELECT " +
                    "id_currency_task FROM user_course WHERE id_user = ? AND id_course = ?");
            statement.setInt(1, id);
            statement.setInt(2, idCourse);
            ResultSet set = statement.executeQuery();
            set.next();
            return set.getInt("id_currency_task");
        } catch (SQLException e) {
            logger.error("exception in usercourse dao");
            throw new UserCourseDaoException();
        }
    }
}
