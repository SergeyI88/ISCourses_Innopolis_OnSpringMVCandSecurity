package db.xml;

import java.io.*;

import db.pojo.*;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.*;

@XmlType(propOrder = {"name", "courses", "userDataList", "taskCourses", "userCourses", "users", "listManager"}, name = "developer")
@XmlRootElement
public class DeveloperBase {
    String name;
    List<Course> courses = new ArrayList<>();
    List<TaskCourse> taskCourses = new ArrayList<>();
    List<UserCourse> userCourses = new ArrayList<>();
    List<UserData> userDataList = new ArrayList<>();
    List<User> users = new ArrayList<>();
    private List<Manager> listManager = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElementWrapper(name = "courses")
    @XmlElement(name = "course")
    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    @XmlElementWrapper(name = "tasks")
    @XmlElement(name = "task")
    public List<TaskCourse> getTaskCourses() {
        return taskCourses;
    }

    public void setTaskCourses(List<TaskCourse> taskCourses) {
        this.taskCourses = taskCourses;
    }

    @XmlElementWrapper(name = "users_courses")
    @XmlElement(name = "user_course")
    public List<UserCourse> getUserCourses() {
        return userCourses;
    }

    public void setUserCourses(List<UserCourse> userCourses) {
        this.userCourses = userCourses;
    }

    @XmlElementWrapper(name = "users_data")
    @XmlElement(name = "user_data")
    public List<UserData> getUserDataList() {
        return userDataList;
    }

    public void setUserDataList(List<UserData> userDataList) {
        this.userDataList = userDataList;
    }

    @XmlElementWrapper(name = "users")
    @XmlElement(name = "user")
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @XmlElementWrapper(name = "managers")
    @XmlElement(name = "manager")
    public List<Manager> getListManager() {
        return listManager;
    }

    public void setListManager(List<Manager> listManager) {
        this.listManager = listManager;
    }


    @Override
    public String toString() {
        return "DeveloperBase{" +
                "name='" + name + '\'' +
                ", userDataList=" + userDataList +
                '}';
    }

}
