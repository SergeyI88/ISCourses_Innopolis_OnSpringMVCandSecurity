package services;

import db.dao.UserDao;
import db.dao.UserDaoImpl;
import db.dao.UserDataDao;
import db.dao.UserDataDaoImpl;
import db.dao.exceptions.UserDaoException;
import db.dao.exceptions.UserDataDaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
@Service
public class ServiceEditPersonalDataAndGet {

    private UserDao userDao;
    private UserDataDao userDataDao;

    public UserDao getUserDao() {
        return userDao;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserDataDao getUserDataDao() {
        return userDataDao;
    }

    @Autowired
    public void setUserDataDao(UserDataDao userDataDao) {
        this.userDataDao = userDataDao;
    }

    public boolean editPassword(String pass, int id_user) throws SQLException, UserDaoException {
        return 1 == userDao.editPassword(id_user, pass);
    }

    public String getName(int id_user) throws SQLException, UserDataDaoException {
        return userDataDao.getNameUserDataFromIdUser(id_user);
    }

    public String getLastName(int id_user) throws SQLException, UserDataDaoException {
        return userDataDao.getLastNameUserDataFromIdUser(id_user);
    }
}
