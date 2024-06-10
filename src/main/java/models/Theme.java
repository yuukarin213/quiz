package models;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Theme implements Serializable {
	  private static final long serialVersionUID = 1L;
	  private final int MAX_THEMENAME_LEN = 255;
	  //! theme id
	  private int id;
	  //! themename
	  private String name;
	  //! theme's status
	  private boolean status;
	  //! validation functions
	  private String[] validators = new String[] {"nameValidator"};

	  // ========================
	  // ===== Constructors =====
	  // ========================
	  /**
	   * @brief constructor
	   */
	  public Theme() {
	    id = 0;
	    name = "";
	    status = true;
	  }

	  /**
	   * @brief constructor
	   * @param[in] int id theme id
	   */
	  public Theme(int id) throws RuntimeException, IOException {
	    List<Theme> themes = Theme.getThemes(String.format("WHERE id = %d", id));

	    //! Extract themename from the collected record
	    if (themes.size() > 0) {
	    	Theme theme = themes.get(0);
	      this.id = theme.getID();
	      name = theme.getName();
	      status = theme.getStatus();
	    }
	    else {
	      throw new RuntimeException(String.format("Invalid theme id (id: %d)", id));
	    }
	  }
	  
	  // ======================
	  // ===== Validators =====
	  // ======================
	  /**
	   * @brief Validate model's variables
	   */
	  public List<String> clean() {
	    List<String> errors = new ArrayList<String>();
	    Class<?> current = this.getClass();

	    for (String functionName: validators) {
	      try {
	        Method method = current.getDeclaredMethod(functionName);
	        method.setAccessible(true);
	        method.invoke(this);
	      }
	      catch (NoSuchMethodException|IllegalAccessException ex) {
	        ex.printStackTrace();
	      }
	      catch (InvocationTargetException ex) {
	        String err = ex.getCause().getMessage();
	        errors.add(err);
	      }
	    }

	    return errors;
	  }

	  /**
	   * @brief Validate themename
	   */
	  private void nameValidator() throws RuntimeException {
	    //! Validation of themename
	    if (Objects.isNull(name)) {
	      throw new RuntimeException("Invalid themename: themename is null.");
	    }
	    if ((0 == name.length()) || (name.length() > MAX_THEMENAME_LEN)) {
	      throw new RuntimeException(String.format("Invalid themename's length (length: %d)", name.length()));
	    }
	    name = name.trim();
	  }

	  // ===============================
	  // ===== Setters and Getters =====
	  // ===============================
	  /**
	   * @brief Set theme id
	   * @param[in] String id theme id
	   */
	  private void setID(int id) {
	    this.id = id;
	  }

	  /**
	   * @brief Get theme id
	   * @return int id
	   */
	  public int getID() {
	    return id;
	  }

	  /**
	   * @brief Set themename
	   * @param[in] String name themename
	   */
	  public void setName(String name) {
	    this.name = name;
	  }

	  /**
	   * @brief Get themename
	   * @return String name
	   */
	  public String getName() {
	    return name;
	  }

	  /**
	   * @brief Set status
	   * @param[in] String status theme's status
	   */
	  public void setStatus(String status) throws RuntimeException {
	    try {
	    	System.out.println(status);
	      this.status = Boolean.parseBoolean(status);
	      System.out.println(this.status);
	    }
	    catch (RuntimeException ex) {
	      throw new RuntimeException("Failed to set status");
	    }
	  }

	  /**
	   * @brief Set status
	   * @param[in] boolean status theme's status
	   */
	  private void setStatus(boolean status) {
		  this.status = status;
	  }

	  /**
	   * @brief Get theme's status
	   * @return boolean status
	   */
	  public boolean  getStatus() {
	    return status;
	  }


	  /**
	   * @brief The function to save model data to database
	   * @param[in] boolean isCreation in the case of creating record
	   */
	  public void save(boolean isCreation) throws SQLException, IOException {
	    try (QueryManager manager = new QueryManager()) {
	      //! In the case of creating the theme
	      if (isCreation) {
	        String sql = String.format("INSERT INTO Theme (name, status) VALUES ('%s', %b) ;", name, status);
	        manager.execUpdate(sql);
	        //! Extract theme's id from the inserted record
	        if (manager.next()) {
	          setID(manager.getInt(1));
	        }
	      }
	      //! In the case of updating the theme information
	      else {
	        String sql = String.format("UPDATE Theme SET name='%s', status=%b WHERE id = %d ;", name, status, id);
	        manager.execUpdate(sql);
	      }
	    }
	  }

	  // ============================
	  // ===== Static functions =====
	  // ============================
	  /**
	   * @brief The function to collect the themes registered to database
	   * @param[in] String condition extracted condition
	   * @return List<Theme> themes list of the matched themes
	   */
	  public static List<Theme> getThemes(String condition) throws IOException {
	    List<Theme> themes = new ArrayList<Theme>();

	    try (QueryManager manager = new QueryManager()) {
	      String sql = String.format("SELECT * FROM Theme %s ;", condition);
	      manager.execSelect(sql);

	      //! Convert the records to Theme's instances
	      while (manager.next()) {
	        //! Create Theme's instance
	    	Theme theme = new Theme();
	        //! Extract data from target record
	        theme.setID(manager.getInt("id"));
	        theme.setName(manager.getString("name"));
	        theme.setStatus(manager.getBoolean("status"));
	        //! Add instance to list
	        themes.add(theme);
	      }
	    }
	    catch(SQLException|RuntimeException ex) {
	      ex.printStackTrace();
	    }

	    return themes;
	  }

}
