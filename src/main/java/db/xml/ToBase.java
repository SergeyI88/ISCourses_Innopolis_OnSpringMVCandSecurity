package db.xml;

import db.pojo.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ToBase {
    public static void main(String[] args) {
        try {
            toXml();
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void toXml() throws JAXBException, ClassNotFoundException {
        File file = new File("base.xml");
        JAXBContext context = JAXBContext.newInstance(DeveloperBase.class);

        Unmarshaller unmarshaller = context.createUnmarshaller();

        DeveloperBase developerBase = (DeveloperBase) unmarshaller.unmarshal(file);
        try {
            setInDB(developerBase);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void setInDB(DeveloperBase developerBase) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/developer",
                "postgres",
                "root");
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO course VALUES " +
                "(?, ?, ?, ?)");

        for (Course course : developerBase.getCourses()) {
            preparedStatement.setInt(1, course.getId());
            preparedStatement.setString(2, course.getName());
            preparedStatement.setString(3, course.getDescription());
            preparedStatement.setInt(4, course.getId_status_of_course());
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();
        setUserData(developerBase);
        connection.close();
    }

    private static void setUserData(DeveloperBase developerBase) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/developer",
                "postgres",
                "root");
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user_data VALUES " +
                "(?, ?, ?, ?)");

        for (UserData user : developerBase.getUserDataList()) {
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getLast_name());
            preparedStatement.setString(4, user.getBirthDay());
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();
        connection.close();
        setManagers(developerBase);
    }

    public static void setManagers(DeveloperBase developerBase) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/developer",
                "postgres",
                "root");
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO manager VALUES " +
                "(?, ?, ?, ?, ?)");

        for (Manager manager : developerBase.getListManager()) {
            preparedStatement.setInt(1, manager.getId());
            preparedStatement.setString(2, manager.getLogin());
            preparedStatement.setString(3, manager.getPassword());
            preparedStatement.setInt(4, manager.getRole());
            preparedStatement.setInt(5, manager.getData_id());
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();
        setTasks(developerBase);
        connection.close();
    }

    private static void setTasks(DeveloperBase developerBase) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/developer",
                "postgres",
                "root");
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO task VALUES " +
                "(?, ?, ?, ?, ?, ?, ?)");

        for (TaskCourse task : developerBase.getTaskCourses()) {
            preparedStatement.setInt(1, task.getId());
            preparedStatement.setInt(2, task.getId_course());
            preparedStatement.setString(3, task.getName());
            preparedStatement.setString(4, task.getDescription());
            preparedStatement.setString(5, task.getAnswer());
            preparedStatement.setString(6, task.getTask());
            preparedStatement.setInt(7, task.getNumber_in_sub_course());
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();
        setUserCourse(developerBase);
        connection.close();
    }



    private static void setUserCourse(DeveloperBase developerBase) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/developer",
                "postgres",
                "root");
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user_course VALUES " +
                "(?, ?, ?, ?, ?)");

        for (UserCourse user : developerBase.getUserCourses()) {
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, user.getId_user());
            preparedStatement.setInt(3, user.getId_course());
            preparedStatement.setInt(4, user.getId_status());
            preparedStatement.setInt(5, user.getId_currency_task());
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();
        setUsers(developerBase);
        connection.close();
    }
    private static void setUsers(DeveloperBase developerBase) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/developer",
                "postgres",
                "root");
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO public.user VALUES " +
                "(?, ?, ?, ?, ?, ?, ?)");

        for (User user : developerBase.getUsers()) {
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setInt(4, user.getData_id());
            preparedStatement.setString(5, user.getDate_reg());
            preparedStatement.setInt(6, user.getLevel());
            preparedStatement.setInt(7, user.getId_rank());
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();
        connection.close();
    }


}
