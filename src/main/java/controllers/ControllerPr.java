package controllers;

import ajax.ListTopUsers;
import ajax.UserA;
import db.pojo.User;
import com.google.gson.Gson;
import db.dao.exceptions.UserDaoException;
import db.dao.exceptions.UserDataDaoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import services.ServiceEditPersonalDataAndGet;
import services.ServiceForWorkUserAndCourse;

import java.sql.SQLException;

@Controller
@SessionAttributes(value = {"user", "name", "last_name"})
public class ControllerPr {
    @Autowired
    ServiceEditPersonalDataAndGet serviceEditPersonalDataAndGet;
    @Autowired
    ServiceForWorkUserAndCourse serviceForWorkUserAndCourse;
    @Autowired
    Gson gson;

    @RequestMapping("/inner/pr")
    public ModelAndView pr() throws SQLException
            , UserDataDaoException {
        User user =
                ((User) SecurityContextHolder
                       .getContext()
                       .getAuthentication()
                       .getPrincipal());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getPrincipal();
        ModelAndView modelAndView = new ModelAndView("pr");
        modelAndView.addObject("name", serviceEditPersonalDataAndGet.getName(user.getId()));
        modelAndView.addObject("user", user);
        modelAndView.addObject("last_name", serviceEditPersonalDataAndGet.getLastName(user.getId()));
        modelAndView.addObject("admin", "admin");
        return modelAndView;
    }

    @RequestMapping("/inner/editpwd")
    @ResponseBody
    public String editPwd(@ModelAttribute("user") User user,
                          @RequestBody String string) throws SQLException, UserDaoException {
        UserA userA = gson.fromJson(string, UserA.class);
        if (serviceEditPersonalDataAndGet.checkPwd(userA.getOldPwd(), user.getId())) {
            serviceEditPersonalDataAndGet.editPassword(userA.getPassword(), user.getId());
            return "yes";
        } else {
            return "bad old password";
        }
    }

    @RequestMapping("/inner/addcourse")
    public String addCourse(@ModelAttribute("user") User user) throws SQLException, UserDaoException {
        return "add_course";
    }

    @RequestMapping("/inner/editname")
    @ResponseBody
    public String editName(@ModelAttribute("user") User user
            , @RequestBody String string) throws SQLException, UserDaoException {
        UserA userA = gson.fromJson(string, UserA.class);

        serviceEditPersonalDataAndGet.editName(userA.getName(), user.getId());
        return "ok";
    }


    @RequestMapping("/inner/editlast")
    @ResponseBody
    public String editLast(@ModelAttribute("user") User user
            , @RequestBody String string) throws SQLException, UserDaoException {
        UserA userA = gson.fromJson(string, UserA.class);
        serviceEditPersonalDataAndGet.editLast(userA.getLast_name(), user.getId());
        return "ok";
    }

    @RequestMapping("/inner/topu")
    @ResponseBody
    public ListTopUsers topTen(@ModelAttribute("user") User user) throws SQLException, UserDaoException {
        ListTopUsers users = new ListTopUsers();
        users.setUserList(serviceEditPersonalDataAndGet.getTopUser());
        return users;
    }
}
