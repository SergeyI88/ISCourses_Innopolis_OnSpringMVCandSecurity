package services;

import db.dao.CourseDao;
import db.dao.UserDao;
import db.pojo.Course;
import db.pojo.TaskCourse;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ServiceShowAndGetCourcesTest {
    @Mock
    CourseDao courseDao;
    @Mock
    UserDao userDao;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    @Test
    public void getAllCourse() throws Exception {
        List<Course> list = new ArrayList<>();
        Course course = new Course();
        course.setName("any");
        list.add(course);
        Mockito.when(courseDao.showAllCourse()).thenReturn(list);
        Assert.assertEquals(list.get(0).getName().toString(), courseDao.showAllCourse().get(0).getName().toString());
        Mockito.verify(courseDao).showAllCourse();
    }

    @Test
    public void getAllCourseByIdUser() throws Exception {
    }

    @Test
    public void getTopCourses() throws Exception {
    }

}