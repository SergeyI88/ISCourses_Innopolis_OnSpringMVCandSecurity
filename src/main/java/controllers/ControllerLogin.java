package controllers;

import db.dao.exceptions.UserDaoException;
import db.dao.exceptions.UserDataDaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import services.ServiceEditPersonalDataAndGet;
import services.ServiceRegAndAuth;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class ControllerLogin extends HttpServlet {
    @Autowired
    private  ServiceRegAndAuth serviceRegAndAuth;
    @Autowired
    private  ServiceEditPersonalDataAndGet serviceEditPersonalDataAndGet;

    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = null;
        String last = null;
        int id = 0;
        try {
            id = serviceRegAndAuth.auth(req.getParameter("login"),
                    req.getParameter("password"));
            if (id != -1) {
                name = serviceEditPersonalDataAndGet.getName(id);
                last = serviceEditPersonalDataAndGet.getLastName(id);
                req.getSession().setAttribute("name", name);
                req.getSession().setAttribute("last_name", last);
                req.getSession().setAttribute("id_user", id);
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/index.jsp");
                requestDispatcher.forward(req, resp);
            } else {
                req.setAttribute("error", "Не обнаружен такой пользователь");
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/login.jsp");
                requestDispatcher.forward(req, resp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (UserDataDaoException e) {
            e.printStackTrace();
        } catch (UserDaoException e) {
            e.printStackTrace();
        }
    }
}
