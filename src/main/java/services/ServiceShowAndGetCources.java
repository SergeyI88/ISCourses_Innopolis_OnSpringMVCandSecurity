package services;

import db.dao.CourseDao;
import db.dao.CourseDaoImpl;
import db.dao.exceptions.CourseDaoException;
import db.pojo.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.*;
@Service
public class ServiceShowAndGetCources {
    public CourseDao getCourseDao() {
        return courseDao;
    }
    @Autowired
    public void setCourseDao(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

   private CourseDao courseDao;

    public List<Course> getAllCourse() throws SQLException {
        List<Course> list = null;
        try {
            list = courseDao.showAllCourse();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (CourseDaoException e) {
            e.printStackTrace();
        }
        return  list;
    }

    public List<Course> getAllCourseByIdUser(int id_user) throws SQLException, CourseDaoException {
        List<Course> list = courseDao.showAllCourseInProfil(id_user);
        return  list;
    }

    public List<Course> getTopCourses() throws SQLException, CourseDaoException {
        List<Course> listTop = courseDao.returnTopTen();
        return listTop;
    }
}
