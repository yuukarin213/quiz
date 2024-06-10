import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Theme;
//! local package
import models.User;

@WebServlet(name="Index", urlPatterns={"/"})
public class Index extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");
    response.setContentType("text/html;charset=UTF-8");

    //! Get user list
    List<User> users = User.getUsers("");
    List<Theme> themes = Theme.getThemes("");
    List<User> creators = User.getCreators();
    //! Setup as an attribute
    request.setAttribute("users", users);
    request.setAttribute("themes", themes);
    request.setAttribute("creators", creators);
    //! Page transition process
    ServletContext context = this.getServletContext();
    RequestDispatcher dispather = context.getRequestDispatcher("/quiz_jsp/index.jsp");
    dispather.forward(request, response);
  }
}