package controllers;

import ajax.CommentA;
import com.google.gson.Gson;
import db.pojo.Comment;
import db.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.servlet.ModelAndView;
import services.ServiceForComment;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;


@Controller
@SessionAttributes({"user"})
public class ControllerComment {
    @Autowired
    ServiceForComment serviceForComment;
    @Autowired
    Gson gson;

    @RequestMapping(value = "/inner/comment", method = RequestMethod.POST)
    @ResponseBody
    public CommentA workAndComment(@RequestBody String string,
                                       @ModelAttribute("user") User user) {
        System.out.println(string);
        Comment comment = gson.fromJson(string, Comment.class);
        CommentA commentA = new CommentA();
        serviceForComment.addCommentToTask(comment.getId_task(), user.getId(), comment.getComment(), System.currentTimeMillis() * 1000);
        commentA.setComment(comment.getComment());
        commentA.setDate(new Date(System.currentTimeMillis() * 1000).toString());
        return commentA;
    }
}
