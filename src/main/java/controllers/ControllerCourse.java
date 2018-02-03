package controllers;

import ajax.CourseA;
import ajax.ReviewA;
import ajax.TaskA;
import com.google.gson.Gson;
import com.sun.org.apache.regexp.internal.RE;
import com.sun.org.apache.xpath.internal.operations.Mod;
import db.dao.exceptions.CourseDaoException;
import db.dao.exceptions.TempDAOException;
import db.pojo.Course;
import db.pojo.TaskCourse;
import db.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.servlet.ModelAndView;
import services.ServiceForWorkTempTable;
import services.ServiceForWorkUserAndCourse;
import services.ServiceShowAndGetCources;

import javax.jws.WebParam;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;


@Controller
public class ControllerCourse extends HttpServlet {
    @Autowired
    ServiceShowAndGetCources serviceShowAndGetCources;
    @Autowired
    ServiceForWorkUserAndCourse serviceForWorkUserAndCourse;
    @Autowired
    ServiceForWorkTempTable serviceForWorkTempTable;
    @Autowired
    Gson gson;

    @ModelAttribute("list")
    public List<Course> getListCourse() throws CourseDaoException, SQLException {
        return serviceShowAndGetCources.getAllCoursesWithRaiting();
    }

    @RequestMapping("/courses")
    public ModelAndView getAllCourses(@SessionAttribute(value = "user", required = false) User user
            , @ModelAttribute("list") List<Course> list) throws SQLException, CourseDaoException {
        ModelAndView modelAndView = new ModelAndView();
        if (user != null) {
            for (Course course : list) {
                if (serviceForWorkUserAndCourse.checkCourseOfUser(user.getId(), course.getId())) {
                    course.setId_status_of_course(1);
                }
            }
        }
        modelAndView.addObject("courses", list);
        return modelAndView;
    }

    @RequestMapping("/inner/review")
    @ResponseBody
    public String putAssessement(@SessionAttribute(value = "user", required = false) User user
            , @RequestBody String string) throws SQLException, CourseDaoException {
        System.out.println(string);
        ReviewA reviewA = gson.fromJson(string, ReviewA.class);
        boolean res = serviceForWorkUserAndCourse.putAssessement(reviewA.getId_course()
                , user.getId()
                , reviewA.getId_assessement(), reviewA.getReview());
        if (res) {
            return "ok";
        } else {
            return "no";
        }
    }

    @RequestMapping(value = "/reviews", method = RequestMethod.POST)

    public ModelAndView getReviews(@RequestParam("id_course") int id_course) throws SQLException, CourseDaoException {
        ModelAndView modelAndView = new ModelAndView("reviews");
        List<ReviewA> reviewAList = serviceForWorkUserAndCourse.getReviewsOfCourse(id_course);
        modelAndView.addObject("reviews", reviewAList);
        System.out.println(reviewAList);
        return modelAndView;
    }

    @RequestMapping(value = "/top")
    public ModelAndView getTop() throws SQLException, CourseDaoException {
        ModelAndView modelAndView = new ModelAndView("index");
        List<Course> top = serviceShowAndGetCources.getTopCourses();
        modelAndView.addObject("top", top);
        return modelAndView;
    }

    @RequestMapping(value = "/inner/newcourse")
    @ResponseBody
    public CourseA newCourse(@RequestBody String string) throws SQLException, CourseDaoException {
        CourseA courseA = gson.fromJson(string, CourseA.class);
        return serviceForWorkTempTable.addCourse(courseA);
    }

    @RequestMapping(value = "/inner/shownewcourses")
    public ModelAndView showNewCourses() throws SQLException, CourseDaoException {
        ModelAndView modelAndView = new ModelAndView("new_courses");
        List<Course> top = null;
        List<CourseA> courses = new ArrayList<>();
        List<TaskCourse> tasks = null;
        try {
            top = serviceForWorkTempTable.getCourses();
            if (top != null) {
                for (Course course : top) {
                    tasks = serviceForWorkTempTable.getTasks(course.getId());
                    System.out.println(tasks);
                    CourseA courseA = new CourseA();
                    courseA.setName(course.getName());
                    courseA.setDesc(course.getDescription());
                    courseA.setTasksC(tasks);
                    courses.add(courseA);
                }
            }
        } catch (TempDAOException e) {
            e.printStackTrace();
        }
        modelAndView.addObject("courses", courses);
        return modelAndView;
    }

    @RequestMapping(value = "/inner/good", method = RequestMethod.POST)
    public ModelAndView accept(@RequestParam CourseA courseA) throws TempDAOException {

        serviceForWorkTempTable.acceptNewCourse(courseA);
        ModelAndView modelAndView = new ModelAndView("new_courses");
        return modelAndView;
    }

    @RequestMapping(value = "/inner/bad", method = RequestMethod.POST)
    public ModelAndView cancell(@RequestParam CourseA courseA) {
        try {
            serviceForWorkTempTable.cancelNewCourse(courseA);
        } catch (TempDAOException e) {
            e.printStackTrace();
        }
        ModelAndView modelAndView = new ModelAndView("new_courses");
        return modelAndView;
    }

}
