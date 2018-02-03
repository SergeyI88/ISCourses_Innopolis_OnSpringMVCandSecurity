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
public class ServiceRegAndAuth {
    private  UserDataDao userDataDao;
    private  UserDao userDao;

    public UserDataDao getUserDataDao() {
        return userDataDao;
    }
    @Autowired
    public void setUserDataDao(UserDataDao userDataDao) {
        this.userDataDao = userDataDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }
    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean registration(String name
            , String last_name
            , String birthday
            , String login
            , String password
            , String date_reg) throws SQLException, UserDaoException, UserDataDaoException {
        if (!userDao.checkOnUniqueLogin(login)) {
            return false;
        }
        userDao.reg(
                login
                , password
                , date_reg
                , userDataDao.reg(
                        name
                        , last_name
                        , birthday));
        return true;
    }
    public int auth(String name, String login)throws UserDaoException {
        return userDao.auth(name, login);
    }
}
