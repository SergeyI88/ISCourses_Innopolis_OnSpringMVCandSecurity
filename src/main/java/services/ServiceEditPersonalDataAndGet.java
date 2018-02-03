package services;

import db.dao.UserDao;
import db.dao.UserDataDao;
import db.dao.exceptions.CourseDaoException;
import db.dao.exceptions.UserDaoException;
import db.dao.exceptions.UserDataDaoException;
import db.pojo.Course;
import db.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

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
    public boolean upCountTask(int id_user) throws SQLException, UserDataDaoException {
        int res = userDataDao.countTasks(id_user);

        return checkOnUpRank(res, id_user);
    }

    private boolean checkOnUpRank(int res, int id_user) throws UserDataDaoException {
        if(res % 50 == 0 && res != 200) {
            return userDataDao.upRank(id_user);
        } else {
            return false;
        }
    }
    public String getCurrencyRank(int id_user) throws UserDaoException {
        return userDao.getRank(id_user);
    }

    public boolean checkPwd(String password, int id) throws UserDaoException {
        return userDao.checkPass(id, password);
    }

    public boolean editName(String name, int id_user) throws SQLException, UserDaoException {
        return  userDao.editName(id_user, name);
    }

    public boolean editLast(String last, int id_user) throws SQLException, UserDaoException {
        return  userDao.editLast(id_user, last);
    }

    public List<User> getTopUser() throws SQLException, UserDaoException {
        List<User> listTop = userDao.topTenUsers();
        return listTop;
    }
}
