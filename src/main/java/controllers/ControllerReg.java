package controllers;

import ajax.UserReg;
import com.google.gson.Gson;
import db.dao.exceptions.UserDaoException;
import db.dao.exceptions.UserDataDaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import services.ServiceRegAndAuth;
import utils.MyPasswordEncoder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

@RestController
public class ControllerReg {
    @Autowired
    private ServiceRegAndAuth serviceRegAndAuth;
    @Autowired
    MyPasswordEncoder myPasswordEncoder;


    @RequestMapping(value = "/reg", method = RequestMethod.POST)

    public String reg(@RequestBody String user) {
        System.out.println(user);
        Gson gson = new Gson();
        UserReg userReg = gson.fromJson(user, UserReg.class);
        System.out.println(userReg.getLast_name());
        try {
            boolean res = serviceRegAndAuth.registration(userReg.getName()
                    , userReg.getLast_name()
                    , "any"
                    , userReg.getLogin()
                    , myPasswordEncoder.encode(userReg.getPass())
                    , new Date(System.currentTimeMillis() * 1000).toString());
            if (res) {
                return "succes";
            }
        } catch (UserDataDaoException e1) {
            e1.printStackTrace();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } catch (UserDaoException e1) {
            e1.printStackTrace();
        }
        return "no";
    }

    @RequestMapping(value = "/reg", method = RequestMethod.GET)
    public String reg() {
        return "Registr";
    }
}
