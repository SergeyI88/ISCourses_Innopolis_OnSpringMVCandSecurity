package db.dao;

import ajax.ReviewA;
import db.dao.exceptions.CourseDaoException;
import db.pojo.Course;

import java.sql.SQLException;
import java.util.List;

public interface CourseDao {

    List<Course> showAllCourse() throws SQLException, ClassNotFoundException, CourseDaoException;

    List<Course> showAllCourseInProfil(int id_user) throws SQLException, CourseDaoException;


    void addCourseInProfilOrBuyHim(int id_user, int id_course) throws SQLException, CourseDaoException;

    boolean checkOfCourseOfUser(int id_user, int id_course) throws SQLException, CourseDaoException;

    Course returnCourseWithAllTasks(int id_course);


    boolean toPutAssessement(int id_course, int id_user, int id_assessement, String review) throws CourseDaoException;

    List<Course> returnTopTen() throws SQLException, CourseDaoException;


    List<Course> returnAllList() throws CourseDaoException;

    int getMaxCountTasksByIdCourse(int idCourse) throws SQLException, CourseDaoException;

    boolean getStatusCourse(int id_course) throws CourseDaoException;

    List<ReviewA> getReviewOfCourse(int id_course) throws CourseDaoException;
}
