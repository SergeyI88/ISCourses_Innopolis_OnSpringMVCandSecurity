package db.dao;

import db.connection.ConnectionManager;
import db.connection.ConnectionManagerPostgres;
import db.dao.exceptions.TaskCourseDaoException;
import db.pojo.Comment;
import db.pojo.TaskCourse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
@Component
public class TaskCourseDaoImpl implements TaskCourseDao {

    private static ConnectionManager connectionManager = ConnectionManagerPostgres.getInstance();
    private static Logger logger = Logger.getLogger(TaskCourseDaoImpl.class);

    @Override
    public List<TaskCourse> getAllTaskByIdCourse(int id_sub_course) throws TaskCourseDaoException {

        List<TaskCourse> taskSubCourseList = new ArrayList<>();
        Connection connection = connectionManager.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT " +
                    "id ," +
                    "id_course,  " +
                    "name, " +
                    "description, " +
                    "answer, " +
                    "task, " +
                    "number_in_course " +
                    "FROM task WHERE id_course = ? ");
            preparedStatement.setInt(1, id_sub_course);
            ResultSet set = preparedStatement.executeQuery();
            while (set.next()) {
                TaskCourse task = new TaskCourse();
                task.setNumber_in_sub_course(set.getInt("number_in_sub_course"));
                task.setId(set.getInt("id"));
                task.setName(set.getString("name"));
                task.setDescription(set.getString("description"));
                task.setAnswer(set.getString("answer"));
                task.setTask(set.getString("task"));
                task.setId_course(set.getInt("id_course"));
                taskSubCourseList.add(task);
            }
            connection.close();
        } catch (SQLException e) {
            logger.error("exception in task dao");
            throw new TaskCourseDaoException();
        }
        return taskSubCourseList;
    }

    @Override
    public TaskCourse getTaskById(int id_task) throws TaskCourseDaoException {

        Connection connection = connectionManager.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT " +
                    "t.id ," +
                    "t.id_sub_course,  " +
                    "t.name as name, " +
                    "t.description as descrip, " +
                    "t.answer, " +
                    "t.task, " +
                    "t.number_in_course, " +
                    "sc.name as name_s, " +
                    "sc.description as descrip_s " +
                    "FROM task t INNER JOIN course sc ON sc.id = t.id_course WHERE id = ? ");
            preparedStatement.setInt(1, id_task);
            ResultSet set = preparedStatement.executeQuery();
            TaskCourse task = new TaskCourse();
            set.next();
            task.setNumber_in_sub_course(set.getInt("number_in_course"));
            task.setId(set.getInt("id"));
            task.setName(set.getString("name"));
            task.setDescription(set.getString("description"));
            task.setAnswer(set.getString("answer"));
            task.setTask(set.getString("task"));
            task.setId_course(set.getInt("id_sub_course"));

            connection.close();
            return task;
        } catch (SQLException e) {
            logger.error("exception in task dao");
            throw new TaskCourseDaoException();
        }
    }

    @Override
    public int getNumberTaskInCourse(int id_user, int id_course) throws TaskCourseDaoException {
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("SELECT " +
                    "id_currency_task FROM user_course " +
                    "WHERE id_user = ? AND id_course = ?");
            statement.setInt(1, id_user);
            statement.setInt(2, id_course);
            ResultSet set = statement.executeQuery();
            boolean res = set.next();
            if (res) {
                return set.getInt("id_currency_task");
            } else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("exception in task dao");
            throw new TaskCourseDaoException();
        }
    }
    @Override
    public TaskCourse getTaskByNumberInCourse(int numberInCourse, int id_course) throws TaskCourseDaoException {
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("SELECT * " +
                    "FROM task " +
                    "WHERE number_in_course = ? AND id_course = ?");

            statement.setInt(1, numberInCourse);
            statement.setInt(2, id_course);
            ResultSet set = statement.executeQuery();
            boolean res = set.next();
            if (res) {
                TaskCourse taskCourse = new TaskCourse();
                taskCourse.setId(set.getInt("id"));
                taskCourse.setId_course(set.getInt("id_course"));
                taskCourse.setName(set.getString("name"));
                taskCourse.setDescription(set.getString("description"));
                taskCourse.setAnswer(set.getString("answer"));
                taskCourse.setTask(set.getString("task"));
                taskCourse.setNumber_in_sub_course(set.getInt("number_in_course"));
                System.out.println(set.getInt("id"));
                System.out.println(set.getInt("id"));
                return taskCourse;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("exception in task dao");
            throw new TaskCourseDaoException();
        }
    }

    @Override
    public void addCommentFromUser(int id_task, int id_user, String comment, long date) throws TaskCourseDaoException {

        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("INSERT INTO task_comment " +
                    "VALUES (DEFAULT, ?, ?, ?, ?);");
            statement.setInt(1, id_user);
            statement.setInt(2, id_task);
            statement.setString(3, comment);
            statement.setLong(4, date);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("exception in task dao");
            throw new TaskCourseDaoException();
        }
    }
    @Override
    public List<Comment> getAllComments(int id_task) throws TaskCourseDaoException {

        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("SELECT tc.comment" +
                    " ,tc.date" +
                    " ,u.login " +
                    "FROM task_comment tc " +
                    "INNER JOIN public.user u ON u.id = tc.id_user " +
                    "WHERE id_task = ?");
            statement.setInt(1, id_task);

            ResultSet set = statement.executeQuery();
            List<Comment> commentList = new ArrayList<>();
            while (set.next()) {
                Comment comment = new Comment();
                comment.setComment(set.getString("comment"));
                comment.setLogin(set.getString("login"));
                comment.setDate(new Date(set.getLong("date")));
                comment.setId_task(id_task);
                commentList.add(comment);
            }
            return commentList;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("exception in task dao");
            throw new TaskCourseDaoException();
        }
    }
}
