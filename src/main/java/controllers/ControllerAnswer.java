package controllers;

import db.dao.exceptions.CourseDaoException;
import db.dao.exceptions.TaskCourseDaoException;
import db.dao.exceptions.UserCourseDaoException;
import db.pojo.TaskCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import services.ServiceForWorkUserAndCourse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/inner/answer")
public class ControllerAnswer extends HttpServlet {
    @Autowired
    ServiceForWorkUserAndCourse service;
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                getServletContext());
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/index.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Boolean answerOnPage;

        Integer id = (Integer) req.getSession().getAttribute("id_user");

        int idCourse = Integer.parseInt(req.getParameter("id_course"));
        TaskCourse taskCourse = null;
        try {
            taskCourse = service.getTaskFromOfProfilByIdCourse(id,
                    idCourse);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (TaskCourseDaoException e) {
            e.printStackTrace();
        } catch (CourseDaoException e) {
            e.printStackTrace();
        }
        String answer = taskCourse.getAnswer();
        String result = req.getParameter("1") != null ? req.getParameter("1") :
                req.getParameter("2") != null ? req.getParameter("2") : req.getParameter("3") != null ?
                        req.getParameter("3") : req.getParameter("4") != null ? req.getParameter("4") : "nothing";

        if (result.equals(answer)) {
            answerOnPage = true;
            try {
                if (service.getMaxTaskByIdCourse(idCourse) == service.getCurrencyTaskByUser(id, idCourse)) {
                    service.changeStatusCourseByUser(id, idCourse);
                } else {
                    service.nextTaskInCourse(id, idCourse);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (CourseDaoException e) {
                e.printStackTrace();
            } catch (UserCourseDaoException e) {
                e.printStackTrace();
            }
        } else {
            answerOnPage = false;
        }
        req.setAttribute("answerOnPage", answerOnPage);
        req.setAttribute("id_course", idCourse);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/answer.jsp");
        requestDispatcher.forward(req, resp);
    }
}
