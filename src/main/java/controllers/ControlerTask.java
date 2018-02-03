package controllers;

import ajax.TaskA;
import com.google.gson.Gson;
import db.dao.exceptions.CourseDaoException;
import db.dao.exceptions.TaskCourseDaoException;
import db.dao.exceptions.UserCourseDaoException;
import db.pojo.Comment;
import db.pojo.TaskCourse;
import db.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.servlet.ModelAndView;
import services.ServiceForComment;
import services.ServiceForWorkUserAndCourse;
import utils.CheckAnswerParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.io.IOException;
import java.sql.SQLException;


@Controller
@SessionAttributes({"user"})
public class ControlerTask {
    @Autowired
    ServiceForWorkUserAndCourse serviceForWorkUserAndCourse;
    @Autowired
    ServiceForComment serviceForComment;
    @Autowired
    Gson gson;



    @ModelAttribute("task")
    public TaskCourse getTask(@ModelAttribute("user") User user,
                              @RequestParam("id_course") Integer idCourse)
            throws TaskCourseDaoException, CourseDaoException, SQLException {
        TaskCourse taskCourse = serviceForWorkUserAndCourse.getTaskFromOfProfilByIdCourse(user.getId(),
                idCourse);
        if (taskCourse == null) {
            taskCourse = new TaskCourse();
        }
        return taskCourse;
    }

    @ModelAttribute("comments")
    public List<Comment> getAllComment(@ModelAttribute("task") TaskCourse taskCourse) {
        List<Comment> list = serviceForComment.getAllCommentToTask(taskCourse.getId());
        if (list == null) {
            Comment comment = new Comment();
            comment.setLogin("ADMIN");
            comment.setComment("Оставьте комментарий первым");
            comment.setDate(new Date(System.currentTimeMillis() * 1000));
            list.add(comment);
        }
        Collections.reverse(list);
        return list;
    }

    @RequestMapping(value = "/inner/task", method = RequestMethod.POST)
    public ModelAndView getTask(
            @RequestParam("id_course") Integer id_course,
            @ModelAttribute("user") User user,
            @ModelAttribute("comments") List<Comment> commentList,
            @ModelAttribute("task") TaskCourse taskCourse) throws SQLException, CourseDaoException, UserCourseDaoException, TaskCourseDaoException {
        if (!serviceForWorkUserAndCourse.checkCourseOfUser(user.getId(), id_course)) {
            ModelAndView modelAndView = new ModelAndView("buy_course");
            return modelAndView;
        } else {
            System.out.println(user.getId());
            if (serviceForWorkUserAndCourse.checkOnDoneCourse(user.getId(), id_course)) {
                ModelAndView modelAndView = new ModelAndView("task_of_course");
                modelAndView.addObject("comments", commentList);
                modelAndView.addObject("task_course", taskCourse);
                modelAndView.addObject("id_task", taskCourse.getId());
                modelAndView.addObject("id_course", id_course);
                return modelAndView;
            } else {
                ModelAndView modelAndView = new ModelAndView("finish");
                modelAndView.addObject("id_course", id_course);
                return modelAndView;
            }
        }
    }
}
