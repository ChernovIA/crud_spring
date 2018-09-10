<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="roles" class="java.util.HashMap" scope="session"/>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <title>add user</title>
</head>
<body>
    <form style="align-content: center" action="/addUser" method="POST">


        <p>Login: <input name="login" type="text" /></p>
        <p>Password: <input name="password" type="password" /></p>
        <p>Name: <input name="name" type="text" /></p>
        <p>Roles:</p>

        </p>
        <select multiple = "true" name="userRoles">
            <c:forEach items="${roles}" var="role">
                <c:choose>
                    <c:when test="${role.value == true}">
                        <option value="${role.key}" selected="true">${role.key}</option>
                    </c:when>
                    <c:otherwise>
                        <option value="${role.key}">${role.key}</option>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </select>
    </p>
        <p><input type="submit" value="add user" /></p>

    </form>

</body>
</html>
