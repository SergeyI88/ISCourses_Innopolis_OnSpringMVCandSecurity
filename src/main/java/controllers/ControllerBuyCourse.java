package controllers;

import db.dao.exceptions.CourseDaoException;
import db.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.servlet.ModelAndView;
import services.ServiceForComment;
import services.ServiceForWorkUserAndCourse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@Controller
@SessionAttributes("user")
public class ControllerBuyCourse {
    @Autowired
    ServiceForWorkUserAndCourse serviceForWorkUserAndCourse;

    @RequestMapping("/inner/buy")
    public String buyCourse(@RequestParam("id_course") Integer id_course,
                            @ModelAttribute("user") User user) throws SQLException, CourseDaoException {
        serviceForWorkUserAndCourse.addCourseInProfil(user.getId(), id_course);
        return "forward:/courses";
    }
}
