package db.pojo;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"id", "id_user", "id_course", "status", "id_task_course", "id_currency_task", "user", "course", "taskCourse", "id_status"}, name = "usercourses")
public class UserCourse {
    private int id;
    private int id_user;
    @XmlTransient
    private User user;
    private int id_course;
    @XmlTransient
    private Course course;
    private int id_status;
    @XmlTransient
    private String status;
    private int id_task_course;
    private int id_currency_task;
    @XmlTransient
    private TaskCourse taskCourse;

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_course() {
        return id_course;
    }

    public void setId_course(int id_course) {
        this.id_course = id_course;
    }

    public int getId_task_course() {
        return id_task_course;
    }

    public void setId_task_course(int id_task_course) {
        this.id_task_course = id_task_course;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TaskCourse getTaskCourse() {
        return taskCourse;
    }

    public void setTaskCourse(TaskCourse taskCourse) {
        this.taskCourse = taskCourse;
    }

    public int getId_status() {
        return id_status;
    }

    public void setId_status(int id_status) {
        this.id_status = id_status;
    }

    public UserCourse() {
    }

    public int getId_currency_task() {
        return id_currency_task;
    }

    public void setId_currency_task(int id_currency_task) {
        this.id_currency_task = id_currency_task;
    }
}
