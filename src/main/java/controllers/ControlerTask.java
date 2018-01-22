package controllers;

import db.dao.exceptions.CourseDaoException;
import db.dao.exceptions.TaskCourseDaoException;
import db.dao.exceptions.UserCourseDaoException;
import db.pojo.Comment;
import db.pojo.TaskCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import services.ServiceForComment;
import services.ServiceForWorkUserAndCourse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/inner/task")
public class ControlerTask extends HttpServlet {
    @Autowired
    ServiceForWorkUserAndCourse serviceForWorkUserAndCourse;
    @Autowired
    ServiceForComment serviceForComment;
    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int idUser = (int) req.getSession().getAttribute("id_user");
        int idCourse = Integer.parseInt(req.getParameter("id_course"));
        try {
            if (!serviceForWorkUserAndCourse.checkCourseOfUser(idUser, idCourse)) {
                resp.getWriter().println("Begin buy this course!");
            } else {
                TaskCourse taskCourse = null;
                if (serviceForWorkUserAndCourse.checkOnDoneCourse(idUser, idCourse)) {
                    taskCourse = serviceForWorkUserAndCourse.getTaskFromOfProfilByIdCourse(idUser,
                            idCourse);
                    List<Comment> listCom = serviceForComment.getAllCommentToTask(taskCourse.getId());
                    if (listCom == null) {
                        listCom = new ArrayList<>();
                        Comment comment = new Comment();
                        comment.setLogin("ADMIN");
                        comment.setComment("Оставьте комментарий первым");
                        comment.setDate(new Date(System.currentTimeMillis() * 1000));
                        listCom.add(comment);
                    }
                    req.setAttribute("id_task", taskCourse.getId());
                    req.setAttribute("comments", listCom);
                    req.setAttribute("course", taskCourse);
                    req.getRequestDispatcher("/task_of_course.jsp").forward(req, resp);
                } else {
                    resp.getWriter().println("You're finished this course!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (CourseDaoException e) {
            e.printStackTrace();
        } catch (UserCourseDaoException e) {
            e.printStackTrace();
        } catch (TaskCourseDaoException e) {
            e.printStackTrace();
        }
    }
}
