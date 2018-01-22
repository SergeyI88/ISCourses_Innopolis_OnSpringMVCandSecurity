package db.pojo;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"id", "name", "description", "id_status_of_course", "statusOfCourse"}, name = "courses")
public class Course {
    private int id;
    private String name;
    private String description;
    private int id_status_of_course;
    @XmlTransient
    private String statusOfCourse;

    public int getId_status_of_course() {
        return id_status_of_course;
    }

    public void setId_status_of_course(int id_status_of_course) {
        this.id_status_of_course = id_status_of_course;
    }

    public String getStatusOfCourse() {
        return statusOfCourse;
    }

    public void setStatusOfCourse(String statusOfCourse) {
        this.statusOfCourse = statusOfCourse;
    }

    public Course() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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


}
