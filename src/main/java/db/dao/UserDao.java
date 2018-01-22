package db.dao;

import db.dao.exceptions.UserDaoException;

import java.sql.SQLException;

public interface UserDao {

    void reg(String login, String password, String date_reg, int id_user_data) throws  UserDaoException;

    int auth(String login, String password) throws  UserDaoException;

    void addSubCourseInProfil(int id_user, int id_sub_course, int id_status) throws  UserDaoException;

    void changeCurrencyTaskOfUser(int id_sub_course, int id_user, int id_next_task_by_course) throws  UserDaoException;

    int editPassword(int id_user, String pass) throws UserDaoException;


    boolean checkOnUniqueLogin(String login) throws  UserDaoException;

}
