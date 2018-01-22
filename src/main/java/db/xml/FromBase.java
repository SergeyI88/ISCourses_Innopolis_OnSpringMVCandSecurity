package db.xml;

import db.connection.ConnectionManager;
import db.connection.ConnectionManagerPostgres;
import db.dao.UserDataDaoImpl;
import db.pojo.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.sql.*;
import java.util.*;
import java.io.*;

public class FromBase {
    static {
        ConnectionManager connectionManager;
    }

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
        DeveloperBase developerBase = new DeveloperBase();

        try {
            getAll(developerBase);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(developerBase, file);
    }

    private static void getAll(DeveloperBase developerBase) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/developer",
                "postgres",
                "root");
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery("SELECT * " +
                "FROM course");
        List<Course> list = new ArrayList<>();
        while (set.next()) {
            Course course = new Course();
            course.setId(set.getInt("id"));
            course.setName(set.getString("name"));
            course.setDescription(set.getString("description"));
            course.setId_status_of_course(set.getInt("id_status_of_course"));
            list.add(course);
        }
        connection.close();
        developerBase.setCourses(list);
        
        getManagers(developerBase);
    }
    
    public static void getManagers(DeveloperBase developerBase) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/developer",
                "postgres",
                "root");
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery("SELECT * " +
                "FROM manager");
        List<Manager> list = new ArrayList<>();
        while (set.next()) {
            Manager manager = new Manager();
            manager.setId(set.getInt("id"));
            manager.setLogin(set.getString("login"));
            manager.setPassword(set.getString("password"));
            manager.setData_id(set.getInt("id_user_data"));
            manager.setRole(set.getInt("id_role"));
            list.add(manager);
        }
        connection.close();
        developerBase.setListManager(list);

        getTasks(developerBase);
    }

    private static void getTasks(DeveloperBase developerBase) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/developer",
                "postgres",
                "root");
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery("SELECT * " +
                "FROM task");
        List<TaskCourse> list = new ArrayList<>();
        while (set.next()) {
            TaskCourse taskCourse = new TaskCourse();
            taskCourse.setId(set.getInt("id"));
            taskCourse.setId_course(set.getInt("id_course"));
            taskCourse.setName(set.getString("name"));
            taskCourse.setDescription(set.getString("description"));
            taskCourse.setAnswer(set.getString("answer"));
            taskCourse.setTask(set.getString("task"));
            taskCourse.setNumber_in_sub_course(set.getInt("number_in_course"));
            list.add(taskCourse);
        }
        connection.close();
        developerBase.setTaskCourses(list);

        getUsers(developerBase);
    }

    private static void getUsers(DeveloperBase developerBase) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/developer",
                "postgres",
                "root");
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery("SELECT * " +
                "FROM public.user ");
        List<User> list = new ArrayList<>();
        while (set.next()) {
            User user = new User();
            user.setId(set.getInt("id"));
            user.setLogin(set.getString("login"));
            user.setPassword(set.getString("password"));
            user.setData_id(set.getInt("user_data_id"));
            user.setDate_reg(set.getString("date_reg"));
            user.setLevel(set.getInt("level"));
            user.setId_rank(set.getInt("id_rank"));
            list.add(user);
        }
        connection.close();
        developerBase.setUsers(list);

        getUserCourse(developerBase);
    }

    private static void getUserCourse(DeveloperBase developerBase) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/developer",
                "postgres",
                "root");
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery("SELECT * " +
                "FROM user_course");
        List<UserCourse> list = new ArrayList<>();
        while (set.next()) {
            UserCourse userCourse = new UserCourse();
            userCourse.setId(set.getInt("id"));
            userCourse.setId_user(set.getInt("id_user"));
            userCourse.setId_course(set.getInt("id_course"));
            userCourse.setId_status(set.getInt("id_status"));
            userCourse.setId_currency_task(set.getInt("id_currency_task"));
            list.add(userCourse);
        }
        connection.close();
        developerBase.setUserCourses(list);

        getUserData(developerBase);
    }

    private static void getUserData(DeveloperBase developerBase) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/developer",
                "postgres",
                "root");
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery("SELECT * " +
                "FROM user_data ");
        List<UserData> list = new ArrayList<>();
        while (set.next()) {
            UserData userData = new UserData();
            userData.setId(set.getInt("id"));
            userData.setName(set.getString("name"));
            userData.setLast_name(set.getString("last_name"));
            userData.setBirthDay(set.getString("birthday"));
            list.add(userData);
        }
        connection.close();
        developerBase.setUserDataList(list);

    }
}
