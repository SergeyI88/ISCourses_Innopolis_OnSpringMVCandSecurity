package services;

import db.dao.*;
import db.dao.exceptions.CourseDaoException;
import db.dao.exceptions.TaskCourseDaoException;
import db.dao.exceptions.UserCourseDaoException;
import db.dao.exceptions.UserDaoException;
import db.pojo.TaskCourse;
import db.pojo.UserCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.*;

import java.sql.SQLException;
@Service
public class ServiceForWorkUserAndCourse {
    private UserCourseDao userCourseDao;
    private TaskCourseDao taskCourseDao;
    private CourseDao courseDao;
    private UserDao userDao;

    public UserCourseDao getUserCourseDao() {
        return userCourseDao;
    }
    @Autowired
    public void setUserCourseDao(UserCourseDao userCourseDao) {
        this.userCourseDao = userCourseDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }
    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public TaskCourseDao getTaskCourseDao() {
        return taskCourseDao;
    }
    @Autowired
    public void setTaskCourseDao(TaskCourseDao taskCourseDao) {
        this.taskCourseDao = taskCourseDao;
    }

    public CourseDao getCourseDao() {
        return courseDao;
    }
    @Autowired
    public void setCourseDao(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    public void addCourseInProfil(int id_user, int id_course) throws SQLException, CourseDaoException {
        courseDao.addCourseInProfilOrBuyHim(id_user, id_course);
    }

    public TaskCourse getTaskById(int id_task) throws SQLException, TaskCourseDaoException {
        return taskCourseDao.getTaskById(id_task);
    }

    public List<TaskCourse> getAllTaskByIdCourse(int id_course) throws SQLException, TaskCourseDaoException {
        return taskCourseDao.getAllTaskByIdCourse(id_course);
    }

    public boolean checkCourseOfUser(int id_user, int id_course) throws SQLException, CourseDaoException {
        return courseDao.checkOfCourseOfUser(id_user, id_course);
    }

    public TaskCourse getTaskFromOfProfilByIdCourse(int id_user, int id_course) throws SQLException, CourseDaoException, TaskCourseDaoException {
        if (!courseDao.checkOfCourseOfUser(id_user, id_course)) {
            courseDao.addCourseInProfilOrBuyHim(id_user, id_course);
        }
        return taskCourseDao.getTaskByNumberInCourse(
                taskCourseDao.getNumberTaskInCourse(id_user, id_course),
                id_course);
    }

    public void nextTaskInCourse(int id_user, int id_course) {
        try {
            int idCurrencyTask = taskCourseDao.getNumberTaskInCourse(id_user, id_course);
            System.out.println(idCurrencyTask + "id already nor,al");

            userDao.changeCurrencyTaskOfUser(id_course, id_user, ++idCurrencyTask);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (TaskCourseDaoException e) {
            e.printStackTrace();
        } catch (UserDaoException e) {
            e.printStackTrace();
        }
    }

    public boolean checkOnDoneCourse(Integer id, int idCourse) throws SQLException, UserCourseDaoException {
        return userCourseDao.checkStatusByCourseAndUser(id, idCourse);
    }

    public int getMaxTaskByIdCourse(int idCourse) throws SQLException, CourseDaoException {
        return courseDao.getMaxCountTasksByIdCourse(idCourse);
    }

    public void changeStatusCourseByUser(Integer id, int idCourse) throws SQLException, UserCourseDaoException {
        userCourseDao.changeStatusCourseByUser(id, idCourse);
    }

    public int getCurrencyTaskByUser(Integer id, int idCourse) throws SQLException, UserCourseDaoException {
        return userCourseDao.getCurrencyIdOfUser(id, idCourse);
    }
}
