package controllers;

import db.dao.exceptions.CourseDaoException;
import db.pojo.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import services.ServiceForWorkUserAndCourse;
import services.ServiceShowAndGetCources;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
@WebServlet("/courses")
public class CourseServlet extends HttpServlet {
    @Autowired
    ServiceShowAndGetCources serviceShowAndGetCources;
    @Autowired
    ServiceForWorkUserAndCourse serviceForWorkUserAndCourse;

    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                getServletContext());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Course> list = null;
        try {
            list = serviceShowAndGetCources.getAllCourse();
            Integer id = (Integer) req.getSession().getAttribute("id_user");
            System.out.println(id + "id_user");
            if (id != null) {
                for (Course course : list) {
                    System.out.println(course.getId() + "id");
                    if (serviceForWorkUserAndCourse.checkCourseOfUser(id, course.getId())) {
                        course.setId_status_of_course(1);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (CourseDaoException e) {
            e.printStackTrace();
        }
        req.setAttribute("courses", list);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("courses.jsp");
        requestDispatcher.forward(req, resp);
    }
}
