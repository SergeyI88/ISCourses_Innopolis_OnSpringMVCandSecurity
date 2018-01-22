package db.dao;

import db.dao.exceptions.TaskCourseDaoException;
import db.pojo.Comment;
import db.pojo.TaskCourse;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface TaskCourseDao {
     List<TaskCourse> getAllTaskByIdCourse(int id_task) throws SQLException, TaskCourseDaoException;

    TaskCourse getTaskById(int id_task) throws SQLException, TaskCourseDaoException;


    int getNumberTaskInCourse(int id_user, int id_course) throws SQLException, TaskCourseDaoException;

    TaskCourse getTaskByNumberInCourse(int numberInCourse, int id_course) throws SQLException, TaskCourseDaoException;




    void addCommentFromUser(int id_task, int id_user, String comment, long date) throws TaskCourseDaoException;

    List<Comment> getAllComments(int id_task) throws TaskCourseDaoException;
}
