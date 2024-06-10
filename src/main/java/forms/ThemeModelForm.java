package forms;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
//! local package
import models.Theme;

public class ThemeModelForm {
  //! the instance of theme model
  Theme theme;
  //! In the case of creating record
  private boolean isCreation;
  //! errors
  private List<String> errors = new ArrayList<String>();
  //! variable-setter-pairs
  private Map<String, String> pairs = new LinkedHashMap<String, String>() {
    {
      put("themename", "setName");
      put("themestatus", "setStatus");
    }
  };

  /**
   * @brief constructor
   */
  public ThemeModelForm() {
	theme = new Theme();
    isCreation = true;
  }

  /**
   * @brief constructor
   * @param[in] int id theme id
   */
  public ThemeModelForm(int id) throws RuntimeException, IOException {
	theme = new Theme(id);
    isCreation = false;
  }

  /**
   * @brief Get form parameters
   * @param[in] HttpServletRequest request request information
   */
  public void getParameter(HttpServletRequest request) {
    String methodName = request.getMethod();

    if ("POST".equals(methodName.toUpperCase())) {
      Class<?> target = theme.getClass();

      for (var key: pairs.keySet()) {
        //! Get body data
        String value = request.getParameter(key);

        try {
          //! Call the Theme's instance method
          Method method = target.getMethod(pairs.get(key), new Class[]{String.class});
          method.invoke(theme, value);
        }
        catch (NoSuchMethodException|IllegalAccessException ex) {
          ex.printStackTrace();
        }
        catch (InvocationTargetException ex) {
          String err = ex.getCause().getMessage();
          errors.add(err);
        }
      }
    }
  }

  /**
   * @brief Validation of the post parameters
   * @return boolean isValid
   * @details true: form data is valid, false: form data is invalid
   */
  public boolean valid() {
    List<String> validatorErrors = theme.clean();
    errors.addAll(validatorErrors);
    boolean isValid = errors.isEmpty();

    return isValid;
  }

  /**
   * @brief Save the model instance to the Database
   */
  public void save() throws SQLException, IOException {
	theme.save(isCreation);
  }

  /**
   * @brief Output the response of user form information
   * @param[in] HttpServletRequest request request information
   * @param[in] HttpServletResponse response response information
   * @param[in] ServletContext context servlet context
   */
  public void executeResponseProcess(HttpServletRequest request, HttpServletResponse response, ServletContext context) throws ServletException, IOException {
      //! Setup as attributes
      request.setAttribute("errors", errors);
      request.setAttribute("action", request.getRequestURI());
      request.setAttribute("theme", theme);
      //! Page transition process
      RequestDispatcher dispather = context.getRequestDispatcher("/quiz_jsp/theme_form.jsp");
      dispather.forward(request, response);
  }
}
