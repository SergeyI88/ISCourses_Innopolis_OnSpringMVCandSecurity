package db.dao;

import db.dao.exceptions.UserDataDaoException;
import db.pojo.User;

import java.sql.SQLException;

public interface UserDataDao {

    int reg(String name, String last_name, String birthday) throws SQLException, UserDataDaoException;

    void editName(int id_user, String name) throws SQLException, UserDataDaoException;

    void editLastName(int id_user, String last_name) throws SQLException, UserDataDaoException;

    String getNameUserDataFromIdUser(int id_user) throws SQLException, UserDataDaoException;

    String getLastNameUserDataFromIdUser(int id_user) throws SQLException, UserDataDaoException;

    int countTasks(int id_user) throws UserDataDaoException;

    boolean upRank(int id_user) throws UserDataDaoException;

}
