package db.dao;

import db.dao.exceptions.ManagerDaoException;

import java.sql.SQLException;

public interface ManagerDao {

    int auth(String login, String password) throws SQLException, ManagerDaoException;
}
