package db.dao;

import db.connection.ConnectionManager;
import db.connection.ConnectionManagerPostgres;
import db.dao.exceptions.CourseDaoException;
import db.pojo.Course;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.*;
@Component
public class CourseDaoImpl implements CourseDao {

    private static Logger logger = Logger.getLogger(CourseDaoImpl.class);
    @Autowired
    private static ConnectionManager connectionManager = ConnectionManagerPostgres.getInstance();

    @Override
    public List<Course> showAllCourse() throws CourseDaoException {
        List<Course> subCourses = new ArrayList<>();
        Connection connection = connectionManager.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT " +
                    "c.id, c.name as name_course, c.description as desc_course, soc.name as name_status " +
                    "FROM course c " +
                    "INNER JOIN s_status_of_course soc ON soc.id = c.id_status_of_course");
            ResultSet set = preparedStatement.executeQuery();
            while (set.next()) {
                Course course = new Course();
                course.setId(set.getInt("id"));
                course.setName(set.getString("name_course"));
                course.setDescription(set.getString("desc_course"));
                course.setStatusOfCourse(set.getString("name_status"));
                subCourses.add(course);
            }
            connection.close();
            return subCourses;
        } catch (SQLException e) {
            logger.error("exception dao course");
            throw new CourseDaoException();
        }
    }

    @Override
    public List<Course> showAllCourseInProfil(int id_user) throws CourseDaoException {
        List<Course> subCourses = new ArrayList<>();
        Connection connection = connectionManager.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT " +
                    "c.id as id, c.name as name, c.description as descip " +
                    "FROM user_course usc INNER JOIN " +
                    "course c on c.id = usc.id_course " +
                    "INNER JOIN task t on t.id = usc.id_currency_task " +
                    "WHERE usc.id_user = ?");
            preparedStatement.setInt(1, id_user);
            ResultSet set = preparedStatement.executeQuery();
            while (set.next()) {
                Course course = new Course();
                course.setId(set.getInt("id"));
                course.setName(set.getString("name"));
                course.setDescription(set.getString("descip"));
                subCourses.add(course);
            }
            connection.close();
        } catch (SQLException e) {
            logger.error("exception dao error");
            throw new CourseDaoException();
        }
        return subCourses;
    }

    @Override
    public void addCourseInProfilOrBuyHim(int id_user, int id_sub_course) throws CourseDaoException {
        Connection connection = connectionManager.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO " +
                    "user_course VALUES (DEFAULT " +
                    ", ? " +
                    ", ? " +
                    ", ? " +
                    ", ? )");
            preparedStatement.setInt(1, id_user);
            preparedStatement.setInt(2, id_sub_course);
            preparedStatement.setInt(3, 1);
            preparedStatement.setInt(4, 1);

            preparedStatement.execute();
            connection.close();
        } catch (SQLException e) {
            logger.error("exception dao course");
            throw new CourseDaoException();
        }
    }

    @Override
    public boolean checkOfCourseOfUser(int id_user, int id_sub_course) throws CourseDaoException {
        Connection connection = connectionManager.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT " +
                    "usc.id " +
                    "FROM user_course usc " +
                    "WHERE usc.id_user = ? AND usc.id_course = ?");
            preparedStatement.setInt(1, id_user);
            preparedStatement.setInt(2, id_sub_course);
            ResultSet set = preparedStatement.executeQuery();
            if (set.next()) {
                return true;
            }
            connection.close();
        } catch (SQLException e) {
            logger.error("exception dao course");
            throw new CourseDaoException();
        }
        return false;
    }

    @Override
    public Course returnCourseWithAllTasks(int id_course) {
        Course course = new Course();
        return course;
    }

    @Override
    public boolean toPutAssessement(int id_course, int id_user, int id_assessement) throws CourseDaoException {
        Connection connectionForCheck = connectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connectionForCheck.prepareStatement("SELECT " +
                    "id FROM raiting_course WHERE id_course = ? AND id_user = ?");
            statement.setInt(1, id_course);
            statement.setInt(2, id_user);
            if (statement.execute()) {
                return false;
            }
            Connection connection = connectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " +
                    "raiting_course VALUES (DEFAULT, ?, ?, ?)");
            preparedStatement.setInt(1, id_course);
            preparedStatement.setInt(2, id_user);
            preparedStatement.setInt(3, id_assessement);
            preparedStatement.execute();
            connection.close();
        } catch (SQLException e) {
            logger.error("exception dao course");
            throw new CourseDaoException();
        }
        return true;
    }

    @Override
    public List<Course> returnTopTen() throws CourseDaoException {
        Connection connection = connectionManager.getConnection();
        List<Course> topCourses = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT " +
                    "  c.name, " +
                    "  c.description, " +
                    "  soc.name, " +
                    "  avg(rc.id_assessement)" +
                    "FROM raiting_course rc " +
                    "  INNER JOIN " +
                    "  course c ON c.id = rc.id_course " +
                    "  INNER JOIN " +
                    "  s_status_of_course soc ON soc.id = c.id_status_of_course " +
                    "GROUP BY c.id, c.description, soc.name " +
                    "ORDER BY c.id DESC " +
                    "LIMIT 10;");
            connection.close();
            topCourses = new ArrayList<>();
        } catch (SQLException e) {
            logger.error("exception dao course");
            throw new CourseDaoException();
        }
        return topCourses;
    }

    @Override
    public int getMaxCountTasksByIdCourse(int idCourse) throws CourseDaoException {
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("SELECT count_tasks " +
                    "FROM course WHERE id = ?");
            statement.setInt(1, idCourse);
            ResultSet set = statement.executeQuery();
            set.next();
            int res = set.getInt("count_tasks");
            connection.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("exception dao course");
            throw new CourseDaoException();
        }
    }
}
