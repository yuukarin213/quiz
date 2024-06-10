import java.io.IOException;
import java.sql.SQLException;

//! local package
import forms.ThemeModelForm;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name="ThemeCreationForm", urlPatterns={"/theme/create-theme"})
public class ThemeCreationForm extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");
    response.setContentType("text/html;charset=UTF-8");

    ThemeModelForm form = new ThemeModelForm();
    form.executeResponseProcess(request, response, this.getServletContext());
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");
    response.setContentType("text/html;charset=UTF-8");

    ThemeModelForm form = new ThemeModelForm();
    form.getParameter(request);

    //! In the case of the validation process is success
    if (form.valid()) {
      try {
        //! Record model data to Database
        form.save();
        //! Page transition process
        response.sendRedirect(request.getContextPath());
      }
      //! In the case of SQL exception
      catch (SQLException ex) {
        String err = "Occur an error inside the HTTP server which execute the SQL statement.";
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, err);
      }
    }
    //! In the case of the validation process is failed
    else {
      form.executeResponseProcess(request, response, this.getServletContext());
    }
  }
}