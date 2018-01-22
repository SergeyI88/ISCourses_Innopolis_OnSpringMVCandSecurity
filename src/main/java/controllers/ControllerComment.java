package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import services.ServiceForComment;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/inner/comment")
public class ControllerComment extends HttpServlet {
    @Autowired
    ServiceForComment serviceForComment;

    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                getServletContext());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int idUser = (int) req.getSession().getAttribute("id_user");
        int idTask = Integer.parseInt(req.getParameter("id_task"));
        String comment = req.getParameter("comment");
        serviceForComment.addCommentToTask(idTask, idUser, comment, System.currentTimeMillis() * 1000);
        req.getRequestDispatcher("/inner/task").forward(req, resp);
    }
}
