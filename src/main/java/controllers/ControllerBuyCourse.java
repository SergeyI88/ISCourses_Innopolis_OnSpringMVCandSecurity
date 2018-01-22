package controllers;

import db.dao.exceptions.CourseDaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import services.ServiceForComment;
import services.ServiceForWorkUserAndCourse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/inner/buy")
public class ControllerBuyCourse extends HttpServlet{
    @Autowired
    ServiceForWorkUserAndCourse serviceForWorkUserAndCourse;

    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                getServletContext());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int idUser = (int) req.getSession().getAttribute("id_user");
        int idCourse = Integer.parseInt(req.getParameter("id_course"));
        try {
            serviceForWorkUserAndCourse.addCourseInProfil(idUser
                    , idCourse);
            req.getRequestDispatcher("/courses").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (CourseDaoException e) {
            e.printStackTrace();
        }
    }
}
