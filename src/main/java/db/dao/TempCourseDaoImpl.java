package db.dao;

import ajax.CourseA;
import ajax.TaskA;
import db.connection.ConnectionManager;
import db.connection.ConnectionManagerPostgres;
import db.dao.exceptions.CourseDaoException;
import db.dao.exceptions.TaskCourseDaoException;
import db.dao.exceptions.TempDAOException;
import db.pojo.Course;
import db.pojo.TaskCourse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class TempCourseDaoImpl implements TempCourseDao {
    private static ConnectionManager connectionManager = ConnectionManagerPostgres.getInstance();
    private static Logger logger = Logger.getLogger(TempCourseDaoImpl.class);


    @Override
    public void addTasks(TaskA[] tasks, int id_course) {
        Connection connection = connectionManager.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " +
                    "temp_task VALUES (DEFAULT, ?, ?, ?, ?, ?, ?)");
            int count = 1;
            for (TaskA taskA : tasks) {
                String answer = taskA.getAnswer();
                StringBuilder stringBuilder = new StringBuilder();
                System.out.println(answer);
                for (String s : answer.split(";")) {
                    stringBuilder.append(s);
                    stringBuilder.append("\n");
                }
                preparedStatement.setInt(1, id_course);
                preparedStatement.setString(2, taskA.getName());
                preparedStatement.setString(3, taskA.getDesc());
                preparedStatement.setString(4, taskA.getTask());
                preparedStatement.setString(5, stringBuilder.toString());
                preparedStatement.setInt(6, count++);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("exception in dao temp");
            e.printStackTrace();
        }

    }

    public int addCourse(String name, String desc, int length) throws TempDAOException {
        Connection connection = connectionManager.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " +
                    "temp_course VALUES (DEFAULT, ?, ?, ?, ?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, desc);
            preparedStatement.setInt(3, 1);
            preparedStatement.setInt(4, length);
            preparedStatement.execute();

            Connection connection2 = connectionManager.getConnection();

            PreparedStatement preparedStatement2 = connection.prepareStatement("SELECT id FROM " +
                    "temp_course WHERE name = ? AND description = ? AND count_tasks = ?");
            preparedStatement2.setString(1, name);
            preparedStatement2.setString(2, desc);
            preparedStatement2.setInt(3, length);
            ResultSet set = preparedStatement2.executeQuery();
            if (set.next()) {
                int res = set.getInt("id");
                connection.close();
                connection2.close();
                return res;
            } else {
                connection.close();
                connection2.close();
            }
            return 0;
        } catch (SQLException e) {
            logger.error("exception in dao temp");
            e.printStackTrace();
            throw new TempDAOException();
        }
    }


    @Override
    public List<Course> showAllCourse() throws TempDAOException {
        List<Course> subCourses = new ArrayList<>();
        Connection connection = connectionManager.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT " +
                    "c.id, c.name as name_course, c.description as desc_course, c.id_status_of_course, soc.name as name_status " +
                    "FROM temp_course c " +
                    "INNER JOIN s_status_of_course soc ON soc.id = c.id_status_of_course");
            ResultSet set = preparedStatement.executeQuery();
            while (set.next()) {
                Course course = new Course();
                course.setId(set.getInt("id"));
                course.setName(set.getString("name_course"));
                course.setId_status_of_course(set.getInt("id_status_of_course"));
                course.setDescription(set.getString("desc_course"));
                course.setStatusOfCourse(set.getString("name_status"));
                subCourses.add(course);
            }
            connection.close();
            return subCourses;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("exception dao course");
            throw new TempDAOException();
        }
    }


    @Override
    public List<TaskCourse> getAllTaskByIdCourse(int id_course) throws TempDAOException {
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
                    "FROM temp_task WHERE id_course = ? ");
            preparedStatement.setInt(1, id_course);
            ResultSet set = preparedStatement.executeQuery();
            while (set.next()) {
                TaskCourse task = new TaskCourse();
                task.setNumber_in_sub_course(set.getInt("number_in_course"));
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
            e.printStackTrace();
            logger.error("exception in task dao");
            throw new TempDAOException();
        }
        return taskSubCourseList;
    }


    public int addCourseInMain(String name, String desc, int size) throws TempDAOException {
        Connection connection = connectionManager.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " +
                    "temp_course VALUES (DEFAULT, ?, ?, ?, ?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, desc);
            preparedStatement.setInt(3, 1);
            preparedStatement.setInt(4, size);
            preparedStatement.execute();

            Connection connection2 = connectionManager.getConnection();

            PreparedStatement preparedStatement2 = connection.prepareStatement("SELECT id FROM " +
                    "temp_course WHERE name = ? AND description = ? AND count_tasks = ?");
            preparedStatement2.setString(1, name);
            preparedStatement2.setString(2, desc);
            preparedStatement2.setInt(3, size);
            ResultSet set = preparedStatement2.executeQuery();
            if (set.next()) {
                int res = set.getInt("id");
                connection.close();
                connection2.close();
                return res;
            } else {
                connection.close();
                connection2.close();
            }
            return 0;
        } catch (SQLException e) {
            logger.error("exception in dao temp");
            e.printStackTrace();
            throw new TempDAOException();
        }
    }

    public int removeTasks(CourseA courseA) throws TempDAOException {
        Connection connection = connectionManager.getConnection();
        Connection connection1 = connectionManager.getConnection();
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement1 = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT id " +
                    "FROM temp_course WHERE name = ? AND description = ?");
            preparedStatement.setString(1, courseA.getName());
            preparedStatement.setString(2, courseA.getDesc());
            ResultSet set = preparedStatement.executeQuery();
            int res = 0;
            if (set.next()) {
                res = set.getInt("id");
            }
            preparedStatement1 = connection1.prepareStatement("DELETE " +
                    "FROM temp_task WHERE id_course = ? ");
            preparedStatement1.setInt(1, res);
            preparedStatement1.execute();
            connection.close();
            connection1.close();
            return res;
        } catch (SQLException e) {
            logger.error("exception in task dao");
            throw new TempDAOException();
        }
    }

    public void removeCourse(int id_course) {
        Connection connection = connectionManager.getConnection();

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("DELETE " +
                    "FROM temp_task WHERE id_course = ? ");
            preparedStatement.setInt(1, id_course);
            preparedStatement.execute();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
