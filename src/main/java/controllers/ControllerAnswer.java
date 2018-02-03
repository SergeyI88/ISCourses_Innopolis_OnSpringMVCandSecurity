package controllers;

import ajax.Answer;
import ajax.TaskA;
import com.google.gson.Gson;
import db.dao.exceptions.CourseDaoException;
import db.dao.exceptions.UserCourseDaoException;
import db.dao.exceptions.UserDaoException;
import db.dao.exceptions.UserDataDaoException;
import db.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import services.ServiceEditPersonalDataAndGet;
import services.ServiceForWorkUserAndCourse;
import utils.CheckAnswerParser;

import java.sql.SQLException;


@Controller
@SessionAttributes("user")
public class ControllerAnswer {
    @Autowired
    ServiceForWorkUserAndCourse service;
    @Autowired
    ServiceEditPersonalDataAndGet serviceEditPersonalDataAndGet;
    @Autowired
    Gson gson;
    @Autowired
    CheckAnswerParser checkAnswerParser;

    @RequestMapping(value = "/inner/answer", method = RequestMethod.POST)
    @ResponseBody
    public Answer equalsAnswer(@RequestBody String string,
                               @ModelAttribute("user") User user) throws UserCourseDaoException
            , SQLException, CourseDaoException, UserDataDaoException, UserDaoException {
        Answer answer = gson.fromJson(string, Answer.class);
        int id_course = Integer.parseInt(answer.getId_course());
        answer.setRight(false);
        if (answer.getAnswer().equals(answer.getHim())) {
            answer.setRight(true);
            if (serviceEditPersonalDataAndGet.upCountTask(user.getId())) {
                answer.setRank(serviceEditPersonalDataAndGet.getCurrencyRank(user.getId()));
            }
            if (service.getMaxTaskByIdCourse(id_course) == service.getCurrencyTaskByUser(user.getId(), id_course)) {
                service.changeStatusCourseByUser(user.getId(), id_course);
            } else {
                service.nextTaskInCourse(user.getId(), id_course);
            }
        }
        return answer;
    }

    @RequestMapping("inner/checktask")
    @ResponseBody
    public TaskA checkTaskUser(@RequestBody String string) {
        System.out.println(string);
        TaskA taskA = gson.fromJson(string, TaskA.class);
        System.out.println(taskA.getAnswer());
        return checkAnswerParser.checkTask(taskA);

    }
}
