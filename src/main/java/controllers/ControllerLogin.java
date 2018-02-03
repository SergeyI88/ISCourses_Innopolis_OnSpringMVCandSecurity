package controllers;

import com.google.gson.Gson;
import db.dao.exceptions.UserDaoException;
import db.dao.exceptions.UserDataDaoException;
import db.pojo.User;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import services.ServiceEditPersonalDataAndGet;
import services.ServiceRegAndAuth;
import java.sql.SQLException;

@Controller
@SessionAttributes(value = {"user", "name", "last_name"} )
public class ControllerLogin {
    @Autowired
    private ServiceRegAndAuth serviceRegAndAuth;
    @Autowired
    private ServiceEditPersonalDataAndGet serviceEditPersonalDataAndGet;

    @RequestMapping(value = "/access", method = RequestMethod.POST)
    public ModelAndView returnOk(@RequestParam(value = "username", required = false) String login,
                                 @RequestParam(value = "userpass", required = false) String pass) throws UserDaoException, SQLException, UserDataDaoException {
//        Gson gson = new Gson();
//        User user = gson.fromJson(string, User.class);
        User user = new User();
        System.out.println(user.getPassword());
        int id_user = serviceRegAndAuth.auth(login, pass);
        if (id_user != -1) {
            ModelAndView modelAndView = new ModelAndView("forward:/inner/pr");
            user.setId(id_user);
            modelAndView.addObject("user", user);
            modelAndView.addObject("name", serviceEditPersonalDataAndGet.getName(id_user));
            modelAndView.addObject("last_name", serviceEditPersonalDataAndGet.getLastName(id_user));
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("login");
            modelAndView.addObject("error", "Не обнаружен такой пользователь");
            return modelAndView;
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String returnOk() throws UserDaoException, SQLException, UserDataDaoException {
        return "login";
    }
}
