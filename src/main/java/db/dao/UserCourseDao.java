package db.dao;

import db.dao.exceptions.UserCourseDaoException;

import java.sql.SQLException;

public interface UserCourseDao {


    boolean checkStatusByCourseAndUser(Integer id, int idCourse) throws SQLException, UserCourseDaoException;

    void changeStatusCourseByUser(Integer id, int idCourse) throws SQLException, UserCourseDaoException;

    int getCurrencyIdOfUser(Integer id, int idCourse) throws SQLException, UserCourseDaoException;
}
