package servlet;

import exceptions.DBException;
import model.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet(urlPatterns = {""})

public class AllUsersServlet extends HttpServlet {

    UserService usSr = UserService.getInstance();

    @Override
    public void init() {
        try {
            usSr.createTable();
        } catch (DBException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> users = usSr.getAllUsers();
        request.setAttribute("users", users);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF8");
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        if(name.trim().length() == 0 || password.trim().length() == 0) {
            doGet(request, response);
        } else {
            User user = new User(name, password);
            usSr.addUser(user);
            doGet(request, response);
        }
    }
}
