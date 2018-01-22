package controllers;

import db.dao.exceptions.UserDaoException;
import db.dao.exceptions.UserDataDaoException;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import services.ServiceRegAndAuth;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/registration")
public class RegServlet extends HttpServlet{

    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                getServletContext());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String pass = req.getParameter("pass");
        String name = req.getParameter("name");
        String last = req.getParameter("last");
        System.out.println(login);
        System.out.println(pass);
        System.out.println(name);
        System.out.println(last);
        ServiceRegAndAuth serviceRegAndAuth = new ServiceRegAndAuth();
        try {
            if (!"".equals(login) && !"".equals(login) && !"".equals(login) && !"".equals("")) {
                System.out.println("зашли в метод");
                boolean res = serviceRegAndAuth.registration(name, last, "any", login, pass, "16-01-2018");
                if (!res) {
                    req.setAttribute("messageFromReg", "Такой пользователь уже есть");
                    RequestDispatcher rd = req.getRequestDispatcher("Registr.jsp");
                    rd.forward(req, resp);
                } else {
                    req.setAttribute("login", login);
                    req.setAttribute("pass", pass);
                    req.setAttribute("messageFromReg", "Регистрация прошла успешно");
                    RequestDispatcher rd = req.getRequestDispatcher("index.jsp");
                    rd.forward(req, resp);
                }
            } else {
                req.setAttribute("messageFromReg", "Пустые поля");
                RequestDispatcher rd = req.getRequestDispatcher("Registr.jsp");
                rd.forward(req, resp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (UserDaoException e) {
            e.printStackTrace();
        } catch (UserDataDaoException e) {
            e.printStackTrace();
        }


    }
}
