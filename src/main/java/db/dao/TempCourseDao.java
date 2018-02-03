package db.dao;

import ajax.TaskA;
import db.dao.exceptions.CourseDaoException;
import db.dao.exceptions.TaskCourseDaoException;
import db.dao.exceptions.TempDAOException;
import db.pojo.Course;
import db.pojo.TaskCourse;

import java.util.List;

public interface TempCourseDao {

    void addTasks(TaskA[] tasks, int id_course);

    List<Course> showAllCourse() throws CourseDaoException, TempDAOException;

    List<TaskCourse> getAllTaskByIdCourse(int id_sub_course) throws TaskCourseDaoException, TempDAOException;
}
