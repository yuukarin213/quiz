<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- Define common variables --%>
<c:set var="appPath" scope="page">${pageContext.request.contextPath}</c:set>
<c:import url="/base.jsp" charEncoding="UTF-8">
  <c:param name="content">
    <div class="p-4">
      <div class="row mt-2">
        <div class="col">
          <p class="h2">Sample App</p>
        </div>
      </div>
      <%-- In the case of User --%>>
      <div class="row p-2">
        <div class="col">
          <div class="row">
            <div class="col">
              <p class="h3 text-decoration-underline">Register/Update User</p>
            </div>
          </div>
          <div class="row row-cols-1 row-cols-md-3 g-2 mt-2">
            <div class="col">
              <select name="user-list" class="form-select" aria-label="User list">
                <c:forEach var="user" items="${users}">
                  <option value="${user.getID()}">
                    <c:out value="${user.getName()}" />
                  </option>
                </c:forEach>
              </select>
            </div>
            <div class="col">
              <%-- Set URL of the user form to "updateUserUrl" variable --%>
              <c:choose>
                <c:when test="${users.size() > 0}">
                  <c:set var="updateUserUrl" scope="page">${appPath}/user/${users.get(0).getID()}</c:set>
                </c:when>
                <c:otherwise>
                  <c:set var="updateUserUrl" scope="page">#</c:set>
                </c:otherwise>
              </c:choose>
              <a href="${updateUserUrl}" id="update-user" class="btn btn-primary w-100 custom-boxshadow-effect">
                Update user's information
              </a>
              <%-- Remove "updateUserUrl" variable --%>
              <c:remove var="updateUserUrl" scope="page" />
            </div>
            <div class="col">
              <a href="${appPath}/user/create-user" class="btn btn-success w-100 custom-boxshadow-effect">
                Register user
              </a>
            </div>
          </div>
        </div>
      </div>
 
 	  <%-- In the case of Theme --%>
 	  <div class="row p-2">
        <div class="col">
          <div class="row">
            <div class="col">
              <p class="h3 text-decoration-underline">Register/Update Theme</p>
            </div>
          </div>
          <div class="row row-cols-1 row-cols-md-3 g-2 mt-2">
            <div class="col">
              <select name="theme-list" class="form-select" aria-label="Theme list">
                <c:forEach var="theme" items="${themes}">
                  <option value="${theme.getID()}">
                    <c:out value="${theme.getName()}" />
                  </option>
                </c:forEach>
              </select>
            </div>
            <div class="col">
              <%-- Set URL of the theme form to "updateThemeUrl" variable --%>
              <c:choose>
                <c:when test="${themes.size() > 0}">
                  <c:set var="updateThemeUrl" scope="page">${appPath}/theme/${themes.get(0).getID()}</c:set>
                </c:when>
                <c:otherwise>
                  <c:set var="updateThemeUrl" scope="page">#</c:set>
                </c:otherwise>
              </c:choose>
              <a href="${updateThemeUrl}" id="update-theme" class="btn btn-primary w-100 custom-boxshadow-effect">
                Update theme's information
              </a>
              <%-- Remove "updateThemeUrl" variable --%>
              <c:remove var="updateThemeUrl" scope="page" />
            </div>
            <div class="col">
              <a href="${appPath}/theme/create-theme" class="btn btn-success w-100 custom-boxshadow-effect">
                Register theme
              </a>
            </div>
          </div>
        </div>
      </div>

      <%-- In the case of Creator --%>>
      <div class="row p-2">
        <div class="col">
          <div class="row">
            <div class="col">
              <p class="h3 text-decoration-underline">Register/Update Quiz</p>
            </div>
          </div>
          <div class="row row-cols-1 row-cols-md-3 g-2 mt-2">
            <div class="col">
              <select name="creator-list" class="form-select" aria-label="Creator list">
                <c:forEach var="creator" items="${creators}">
                  <option value="${creator.getID()}">
                    <c:out value="${creator.getName()}" />
                  </option>
                </c:forEach>
              </select>
            </div>
            <div class="col">
              <%-- Set URL of the user form to "createQuizUrl" variable --%>
              <c:choose>
                <c:when test="${creators.size() > 0}">
                  <c:set var="createQuizUrl" scope="page">${appPath}/quiz/${creators.get(0).getID()}</c:set>
                </c:when>
                <c:otherwise>
                  <c:set var="createQuizUrl" scope="page">#</c:set>
                </c:otherwise>
              </c:choose>
              <a href="${createQuizUrl}" id="create-quiz" class="btn btn-primary w-100 custom-boxshadow-effect">
                Create Quiz
              </a>
              <%-- Remove "createQuizUrl" variable --%>
              <c:remove var="createQuizUrl" scope="page" />
            </div>
          </div>
        </div>
      </div>

    </div>
  </c:param>
  <c:param name="script">
    <script>
      (function () {
        const contextPath = '${appPath}';
        const menus = {
          'update-user':   {name: 'user-list',    prefix: 'user'},
          'update-theme':  {name: 'theme-list',   prefix: 'theme'},
          'create-quiz':   {name: 'creator-list', prefix: 'quiz'},
        };
        for (const tagID of Object.keys(menus)) {
          const target = menus[tagID];
          const element = document.querySelector('select[name="' + target.name + '"]');
          element.addEventListener('change', (event) => {
            const value = event.target.value;
            const _tag = document.querySelector('#' + tagID);
            _tag.href = contextPath + '/' + target.prefix + '/' + value;
          });
        }
      })();
    </script>
  </c:param>
</c:import>
