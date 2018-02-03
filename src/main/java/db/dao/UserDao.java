package db.dao;

import db.dao.exceptions.UserDaoException;
import db.dao.exceptions.UserDataDaoException;
import db.pojo.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {

    void reg(String login, String password, String date_reg, int id_user_data) throws  UserDaoException;

    int auth(String login, String password) throws  UserDaoException;

    void addSubCourseInProfil(int id_user, int id_sub_course, int id_status) throws  UserDaoException;

    void changeCurrencyTaskOfUser(int id_sub_course, int id_user, int id_next_task_by_course) throws  UserDaoException;

    int editPassword(int id_user, String pass) throws UserDaoException;


    boolean checkOnUniqueLogin(String login) throws  UserDaoException;

    String getRank(int id_user) throws UserDaoException;

    boolean checkPass(int id, String password) throws UserDaoException;

    boolean editName(int id_user, String name) throws UserDaoException;

    boolean editLast(int id_user, String last) throws UserDaoException;

    List<User> topTenUsers() throws UserDaoException;


    User getLoginAndPassAndId(String username) throws UserDaoException;
}
