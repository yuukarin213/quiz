<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="/base.jsp" charEncoding="UTF-8">
  <c:param name="content">
    <div class="p-4">
      <div class="row mt-2">
        <div class="col">
          <p class="h2">Register/Update Theme</p>
        </div>
      </div>
      <div class="row p-2">
        <div class="col">
          <form action="${action}" method="POST">
            <c:if test="${not errors.isEmpty()}">
              <div class="row mt-2">
                <div class="col">
                  <ul class="error-message">
                    <c:forEach var="err" items="${errors}">
                      <li><c:out value="${err}" /></li>
                    </c:forEach>
                  </ul>
                </div>
              </div>
            </c:if>
            <div class="row row-cols-1 g-2 mt-2">
              <div class="col">
                <label for="themename" class="form-label">Theme name</label>
                <input type="text" class="form-control" id="themename" name="themename" value="${theme.getName()}" placeholder="Enter the theme name" />
              </div>
              <div class="col">
				<select name="themestatus" id="themestatus" class="form-select" aria-label="Theme status">
					<option value="true" <c:if test="${theme.getStatus()}">selected</c:if>>Valid</option>
					<option value="false" <c:if test="${not theme.getStatus()}">selected</c:if>>Invalid</option>
				</select>
              </div>
            </div>
            <div class="row row-cols-1 row-cols-md-2 g-2 mt-2">
              <div class="col">
                <button type="submit" class="btn btn-primary w-100 custom-boxshadow-effect">
                  Register/Update
                </button>
              </div>
              <div class="col">
                <a href="${pageContext.request.contextPath}" class="btn btn-secondary w-100 custom-boxshadow-effect">
                  Back
                </a>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  </c:param>
</c:import>
