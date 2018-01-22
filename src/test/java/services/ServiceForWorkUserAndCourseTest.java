package services;

import db.connection.DbConnection;
import db.dao.CourseDao;
import db.dao.TaskCourseDao;
import db.dao.UserCourseDao;
import db.dao.UserDao;
import db.pojo.TaskCourse;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;


import java.util.*;

import static org.junit.Assert.*;

public class ServiceForWorkUserAndCourseTest {
    @Mock
    TaskCourseDao taskCourseDao;
    @Mock
    CourseDao courseDao;
    @Mock
    UserCourseDao userCoursedao;
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();



    @Test
    public void getTaskById() throws Exception {
        TaskCourse taskCourse = new TaskCourse();
        taskCourse.setTask("any");
        Mockito.when(taskCourseDao.getTaskById(2)).thenReturn(taskCourse);
        Assert.assertEquals(taskCourse.getTask(), taskCourseDao.getTaskById(2).getTask().toString());
        Mockito.verify(taskCourseDao).getTaskById(2);
    }

    @Test
    public void getAllTaskByIdCourse() throws Exception {
        List<TaskCourse> list = new ArrayList<>();
        TaskCourse taskCourse = new TaskCourse();
        taskCourse.setTask("any");
        list.add(taskCourse);
        Mockito.when(taskCourseDao.getAllTaskByIdCourse(2)).thenReturn(list);
        Assert.assertEquals(list.get(0).getTask().toString(), taskCourseDao.getAllTaskByIdCourse(2).get(0).getTask().toString());
        Mockito.verify(taskCourseDao).getAllTaskByIdCourse(2);
    }

    @Test
    public void checkCourseOfUser() throws Exception {
        Mockito.when(courseDao.checkOfCourseOfUser(40, 2)).thenReturn(true);
        Assert.assertTrue(courseDao.checkOfCourseOfUser(40, 2));
        Mockito.verify(courseDao).checkOfCourseOfUser(40, 2);
    }


    @Test
    public void checkOnDoneCourse() throws Exception {
        Mockito.when(userCoursedao.checkStatusByCourseAndUser(40, 2)).thenReturn(true);
        Assert.assertTrue(userCoursedao.checkStatusByCourseAndUser(40, 2));
        Mockito.verify(userCoursedao).checkStatusByCourseAndUser(40, 2);
    }

    @Test
    public void getMaxTaskByIdCourse() throws Exception {
        Mockito.when(courseDao.getMaxCountTasksByIdCourse(2)).thenReturn(2);
        Assert.assertEquals(2, courseDao.getMaxCountTasksByIdCourse(2));
        Mockito.verify(courseDao).getMaxCountTasksByIdCourse(2);
    }

}