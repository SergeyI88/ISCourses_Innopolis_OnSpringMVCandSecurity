package db.pojo;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"id", "name", "id_course", "description"
        , "answer", "task", "number_in_sub_course", "course"}, name = "taskCourses")
public class TaskCourse {
    private int id;
    private int id_course;
    @XmlTransient
    private Course course;
    private String name;
    private String description;
    private String answer;
    private String task;
    private int number_in_sub_course;

    public int getNumber_in_sub_course() {
        return number_in_sub_course;
    }

    public void setNumber_in_sub_course(int number_in_sub_course) {
        this.number_in_sub_course = number_in_sub_course;
    }

    public TaskCourse() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_course() {
        return id_course;
    }

    public void setId_course(int id_curse) {
        this.id_course = id_curse;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
